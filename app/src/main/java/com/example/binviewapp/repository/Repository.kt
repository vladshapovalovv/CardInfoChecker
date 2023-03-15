package com.example.binviewapp.repository

import com.example.binviewapp.GetCardInfoByBinResponse
import com.example.binviewapp.NetworkLayer

class Repository {

    suspend fun getCardInfoByBin(cardBin: Int): GetCardInfoByBinResponse? {
        val request = NetworkLayer.apiClient.getCardInfoByBin(cardBin)

        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }

}