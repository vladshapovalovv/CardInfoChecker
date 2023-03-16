package com.example.binviewapp.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.binviewapp.R
import com.example.binviewapp.databinding.CardInfoActivityBinding
import com.example.binviewapp.storage.AppDatabase
import com.example.binviewapp.storage.entity.BinEntity
import com.example.binviewapp.viewmodel.DatabaseViewModel
import com.example.binviewapp.viewmodel.NetworkViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.util.*

class CardInfoActivity : AppCompatActivity() {

    private lateinit var binding: CardInfoActivityBinding

    private val networkViewModel: NetworkViewModel by lazy {
        ViewModelProvider(this)[NetworkViewModel::class.java]
    }
    private val databaseViewModel: DatabaseViewModel by lazy {
        ViewModelProvider(this)[DatabaseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardInfoActivityBinding.inflate(layoutInflater).also { setContentView(it.root) }

        databaseViewModel.init(AppDatabase.getDatabase(this))

        processUserInput()
        initFieldsViaLiveData(getString(R.string.default_request).toInt())
        validateUserInput()

        binding.clearInputButton.setOnClickListener {
            binding.cardInfoEditText.text.clear()
        }

        binding.cardInfoEditText.imeOptions = EditorInfo.IME_ACTION_DONE

        onKeyboardDoneClick(binding.cardInfoEditText)
    }

    private fun validateUserInput() {
        var binHistoryList = ArrayList<String>()

        databaseViewModel.binEntitiesLiveData.observe(this) { binEntityList ->
            createHistoryAdapter(binEntityList.map { it.bin } as ArrayList<String>)
        }


        binding.searchButton.setOnClickListener {
            val userInput = binding.cardInfoEditText.text
            if (userInput.isNullOrBlank()) {
                binding.CardBinInputLayout.error = "This field can't empty!"
                return@setOnClickListener
            }
            val binEntity = BinEntity(
                id = UUID.randomUUID().toString(),
                bin = userInput.toString()
            )
            databaseViewModel.insertItem(binEntity)
            initFieldsViaLiveData(userInput
                .toString()
                .filterNot { it.isWhitespace() }
                .toInt())
                hideKeyboard()
        }
    }

    private fun createHistoryAdapter(binList: ArrayList<String>) {
        val adapter = ArrayAdapter(this, R.layout.dropdown_history, binList)
        binding.cardInfoEditText.setAdapter(adapter)
    }

    private fun initFieldsViaLiveData(cardBin: Int) {

        networkViewModel.refreshCardInfo(cardBin)

        networkViewModel.cardInfoByLiveData.observe(this) { response ->
            if (response == null) {
                binding.CardBinInputLayout.error = "Wrong card number"
                setAllFieldsLoadingState()
                return@observe
            }
            with(binding) {
                binding.CardBinInputLayout.error = null
                schemeField.text = response.scheme
                cardTypeField.text = response.type
                brandField.text = response.brand

                if (response.prepaid) {
                    prepaidField.text = getString(R.string.card_field_positive)
                } else prepaidField.text = getString(R.string.card_field_negative)

                lengthField.text = response.number.length.toString()

                if (response.number.luhn) {
                    LUHNField.text = getString(R.string.card_field_positive)
                } else LUHNField.text = getString(R.string.card_field_negative)

                countryField.text = buildString {
                    append(response.country.emoji)
                    append(" ")
                    append(response.country.name)
                }
                //countryCoordinatesField.text
                countryCurrencyField.text = buildString{
                    append(getString(R.string.card_field_start_currency))
                    append(response.country.currency)
                }
                bankField.text = buildString {
                    append(response.bank.name)
                    if (!response.bank.city.isNullOrBlank()) {
                        append(", ")
                    }
                    append(response.bank.city)
                }
                urlField.text = response.bank.url
                phoneNumberField.text = response.bank.phone

                if ((response.country.latitude and response.country.longitude) == 0) {
                    binding.countryCurrencyField.text = getString(R.string.location_unknown)
                    return@observe
                } else {
                    val latitude = response.country.latitude
                    val longitude = response.country.longitude
                    binding.countryCoordinatesField.setOnClickListener {
                        sendLocationIntent(latitude, longitude)
                    }
                }

            }
        }
    }

    private fun setAllFieldsLoadingState() {
        val listOfFields = listOf(
            binding.schemeField,
            binding.cardTypeField,
            binding.brandField,
            binding.prepaidField,
            binding.lengthField,
            binding.LUHNField,
            binding.countryField,
            binding.countryCurrencyField,
            binding.bankField,
            binding.urlField,
            binding.phoneNumberField,
        )
        listOfFields.forEach { it.text = getString(R.string.text_loading_state) }
    }

    private fun processUserInput() {
        binding.cardInfoEditText.doAfterTextChanged { text ->
            val formattedText = text.toString().replace(" ", "").chunked(4).joinToString(" ")
            if (formattedText != text.toString()) {
                binding.cardInfoEditText.setText(formattedText)
                binding.cardInfoEditText.setSelection(binding.cardInfoEditText.length())
            }
        }

    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.cardInfoEditText.windowToken, 0)
    }

    private fun sendLocationIntent(latitude: Int, longitude: Int) {
        try {
            val intentUri = Uri.parse("geo:$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            //mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        } catch (e: java.lang.Exception) {
            Snackbar.make(binding.root, getString(R.string.no_map_error), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun onKeyboardDoneClick(search: MaterialAutoCompleteTextView){
        search.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
    }

}