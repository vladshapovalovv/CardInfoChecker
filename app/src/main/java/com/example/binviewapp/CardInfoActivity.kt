package com.example.binviewapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CardInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_info_activity)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val cardInfoService: CardInfoService = retrofit.create(CardInfoService::class.java)

        cardInfoService.getCardInfoByBin(427630).enqueue(object: Callback<GetCardInfoByBin>{
            override fun onResponse(call: Call<GetCardInfoByBin>, response: Response<GetCardInfoByBin>) {
               Log.i("SUCCESS", response.toString())

                if(!response.isSuccessful){
                    Toast.makeText(this@CardInfoActivity, "unsuccessful network call", Toast.LENGTH_SHORT).show()
                    return
                }
                val body = response.body()
                val name = body?.bank?.name
            }

            override fun onFailure(call: Call<GetCardInfoByBin>, t: Throwable) {
                Log.i("Main Activity", t.message?:"Null msg")
            }

        })
    }
}