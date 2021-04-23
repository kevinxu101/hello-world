package com.example.sampleapi


import com.google.gson.annotations.SerializedName

data class AddressValidationModel(
    @SerializedName("error")
    var error: String,
    @SerializedName("status")
    var status: Boolean
)