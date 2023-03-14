package com.example.binviewapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CardInfoService {

    @GET("{card-bin}")
    fun getCardInfoByBin(
        @Path("card-bin") cardBin: Int
    ): Call<GetCardInfoByBin>

}