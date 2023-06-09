package com.example.binviewapp.network

import com.example.binviewapp.network.response.GetCardInfoByBinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CardInfoService {
    @GET("{card-bin}")
    suspend fun getCardInfoByBin(
        @Path("card-bin") cardBin: Int
    ): Response<GetCardInfoByBinResponse>

}