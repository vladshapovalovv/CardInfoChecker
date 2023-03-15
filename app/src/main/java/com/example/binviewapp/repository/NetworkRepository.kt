package com.example.binviewapp.repository

import com.example.binviewapp.network.NetworkLayer
import com.example.binviewapp.network.response.GetCardInfoByBinResponse

class NetworkRepository {

    suspend fun getCardInfoByBin(cardBin: Int): GetCardInfoByBinResponse? {
        val request = NetworkLayer.apiClient.getCardInfoByBin(cardBin)

        if (request.failed) {
            return null
        }

        if (!request.isSuccessful) {
            return null
        }
        return request.body
    }

}