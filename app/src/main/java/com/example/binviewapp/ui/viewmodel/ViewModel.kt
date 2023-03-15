package com.example.binviewapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binviewapp.network.response.GetCardInfoByBinResponse
import com.example.binviewapp.repository.Repository
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {

    private val repository = Repository()

    private val _cardInfoByLiveData = MutableLiveData<GetCardInfoByBinResponse>()

    val cardInfoByLiveData: LiveData<GetCardInfoByBinResponse?> = _cardInfoByLiveData

    fun refreshCardInfo(cardBin: Int){
        viewModelScope.launch {
            val response = repository.getCardInfoByBin(cardBin)
            _cardInfoByLiveData.postValue(response)
        }
    }

}