package com.example.binviewapp

import retrofit2.Response

class ApiClient(
    private val cardInfoService: CardInfoService
){

    suspend fun getCardInfoByBin(cardBin: Int): Response<GetCardInfoByBinResponse>{
        return cardInfoService.getCardInfoByBin(cardBin)
    }

}