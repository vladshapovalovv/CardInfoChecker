package com.example.binviewapp.network

import com.example.binviewapp.network.response.GetCardInfoByBinResponse
import com.example.binviewapp.network.response.NetworkResponse
import retrofit2.Response

class ApiClient(
    private val cardInfoService: CardInfoService
) {
    suspend fun getCardInfoByBin(cardBin: Int): NetworkResponse<GetCardInfoByBinResponse> {
        return safeApiCall { cardInfoService.getCardInfoByBin(cardBin) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): NetworkResponse<T> {
        return try {
            NetworkResponse.success(apiCall.invoke())
        } catch (e: java.lang.Exception) {
            NetworkResponse.failure(e)
        }
    }
}