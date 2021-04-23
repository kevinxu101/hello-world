package com.example.sampleapi


import com.google.gson.annotations.SerializedName

data class CalculationResultModel(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("status")
    var status: Boolean
) {
    data class Data(
        @SerializedName("day")
        var day: Day,
        @SerializedName("hour")
        var hour: Hour,
        @SerializedName("minute")
        var minute: Minute,
        @SerializedName("month")
        var month: Month,
        @SerializedName("prices")
        var prices: Prices,
        @SerializedName("week")
        var week: Week
    ) {
        data class Day(
            @SerializedName("bitcoins")
            var bitcoins: Double,
            @SerializedName("coins")
            var coins: Double,
            @SerializedName("dollars")
            var dollars: Double,
            @SerializedName("euros")
            var euros: Double,
            @SerializedName("pounds")
            var pounds: Double,
            @SerializedName("rubles")
            var rubles: Double,
            @SerializedName("yuan")
            var yuan: Double
        )

        data class Hour(
            @SerializedName("bitcoins")
            var bitcoins: Double,
            @SerializedName("coins")
            var coins: Double,
            @SerializedName("dollars")
            var dollars: Double,
            @SerializedName("euros")
            var euros: Double,
            @SerializedName("pounds")
            var pounds: Double,
            @SerializedName("rubles")
            var rubles: Double,
            @SerializedName("yuan")
            var yuan: Double
        )

        data class Minute(
            @SerializedName("bitcoins")
            var bitcoins: Double,
            @SerializedName("coins")
            var coins: Double,
            @SerializedName("dollars")
            var dollars: Double,
            @SerializedName("euros")
            var euros: Double,
            @SerializedName("pounds")
            var pounds: Double,
            @SerializedName("rubles")
            var rubles: Double,
            @SerializedName("yuan")
            var yuan: Double
        )

        data class Month(
            @SerializedName("bitcoins")
            var bitcoins: Double,
            @SerializedName("coins")
            var coins: Double,
            @SerializedName("dollars")
            var dollars: Double,
            @SerializedName("euros")
            var euros: Double,
            @SerializedName("pounds")
            var pounds: Double,
            @SerializedName("rubles")
            var rubles: Double,
            @SerializedName("yuan")
            var yuan: Double
        )

        data class Prices(
            @SerializedName("price_btc")
            var priceBtc: Double,
            @SerializedName("price_cny")
            var priceCny: Double,
            @SerializedName("price_eur")
            var priceEur: Double,
            @SerializedName("price_gbp")
            var priceGbp: Double,
            @SerializedName("price_rur")
            var priceRur: Int,
            @SerializedName("price_usd")
            var priceUsd: Double
        )

        data class Week(
            @SerializedName("bitcoins")
            var bitcoins: Double,
            @SerializedName("coins")
            var coins: Double,
            @SerializedName("dollars")
            var dollars: Double,
            @SerializedName("euros")
            var euros: Double,
            @SerializedName("pounds")
            var pounds: Double,
            @SerializedName("rubles")
            var rubles: Double,
            @SerializedName("yuan")
            var yuan: Double
        )
    }
}