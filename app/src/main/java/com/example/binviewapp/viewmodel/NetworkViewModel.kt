package com.example.binviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binviewapp.network.response.GetCardInfoByBinResponse
import com.example.binviewapp.repository.NetworkRepository
import kotlinx.coroutines.launch

class NetworkViewModel: ViewModel() {

    private val networkRepository = NetworkRepository()

    private val _cardInfoByLiveData = MutableLiveData<GetCardInfoByBinResponse>()

    val cardInfoByLiveData: LiveData<GetCardInfoByBinResponse?> = _cardInfoByLiveData

    fun refreshCardInfo(cardBin: Int){
        viewModelScope.launch {
            val response = networkRepository.getCardInfoByBin(cardBin)
            _cardInfoByLiveData.postValue(response)
        }
    }



}