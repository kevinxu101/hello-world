package com.example.sampleapi


import com.google.gson.annotations.SerializedName

data class MiningInformationModel(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("status")
    var status: Boolean
) {

    data class Data(
        @SerializedName("account")
        var account: String,
        @SerializedName("avgHashrate")
        var avgHashrate: AvgHashrate,
        @SerializedName("balance")
        var balance: String,
        @SerializedName("hashrate")
        var hashrate: String,
        @SerializedName("unconfirmed_balance")
        var unconfirmedBalance: String,
        @SerializedName("workers")
        var workers: List<Worker>
    ) {
        data class AvgHashrate(
            @SerializedName("h1")
            var h1: String,
            @SerializedName("h12")
            var h12: String,
            @SerializedName("h24")
            var h24: String,
            @SerializedName("h3")
            var h3: String,
            @SerializedName("h6")
            var h6: String
        )

        data class Worker(
            @SerializedName("h1")
            var h1: String,
            @SerializedName("h12")
            var h12: String,
            @SerializedName("h24")
            var h24: String,
            @SerializedName("h3")
            var h3: String,
            @SerializedName("h6")
            var h6: String,
            @SerializedName("hashrate")
            var hashrate: String,
            @SerializedName("id")
            var id: String,
            @SerializedName("lastshare")
            var lastshare: Int,
            @SerializedName("rating")
            var rating: Int,
            @SerializedName("uid")
            var uid: Int
        )
    }
}