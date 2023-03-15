package com.example.binviewapp.network.response

data class GetCardInfoByBinResponse(
    val bank: Bank = Bank(),
    val brand: String = "?",
    val country: Country = Country(),
    val number: Number = Number(),
    val prepaid: Boolean = false,
    val scheme: String = "?",
    val type: String = "?"
) {
    data class Bank(
        val city: String = "",
        val name: String = "?",
        val phone: String = "?",
        val url: String = "?"
    )
    data class Number(
        val length: Int = 0,
        val luhn: Boolean = false
    )
    data class Country(
        val alpha2: String = "?",
        val currency: String = "?",
        val emoji: String = "?",
        val latitude: Int = 0,
        val longitude: Int = 0,
        val name: String = "?",
        val numeric: String = "?"
    )
}





