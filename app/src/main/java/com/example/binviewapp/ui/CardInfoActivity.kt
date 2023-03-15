package com.example.binviewapp.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.binviewapp.R
import com.example.binviewapp.databinding.CardInfoActivityBinding
import com.example.binviewapp.ui.viewmodel.ViewModel
import com.google.android.material.snackbar.Snackbar

class CardInfoActivity : AppCompatActivity() {

    private lateinit var binding: CardInfoActivityBinding
    private val viewModel: ViewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardInfoActivityBinding.inflate(layoutInflater).also { setContentView(it.root) }

        processUserInput()
        initFieldsViaLiveData(getString(R.string.default_request).toInt())

        binding.searchButton.setOnClickListener {
            if(binding.cardInfoEditText.text.isNullOrBlank()){
                binding.CardBinInputLayout.error = "This field can't empty!"
                return@setOnClickListener
            }
            initFieldsViaLiveData(binding.cardInfoEditText
                .text.toString()
                .filterNot { it.isWhitespace() }
                .toInt())
        }
        hideKeyboard(binding.root)
    }

    private fun initFieldsViaLiveData(cardBin: Int) {

        viewModel.refreshCardInfo(cardBin)

        viewModel.cardInfoByLiveData.observe(this) { response ->
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

        /*        binding.cardInfoEditText.addTextChangedListener {
            val followingString = binding.cardInfoEditText.text.toString().filterNot { it.isWhitespace() }
            if (followingString.length >= 5) {
                binding.searchButton.strokeColor = resources.getColor(R.color.purple_200)
                binding.searchButton.strokeWidth = 4
                return@addTextChangedListener
            }
        }*/
    }

    private fun hideKeyboard(view: View){
        val imm: InputMethodManager = application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}