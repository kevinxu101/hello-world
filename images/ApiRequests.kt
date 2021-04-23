package com.example.sampleapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiRequests {

    @GET()
    fun getNanoPoolAPI(@Url string: String): Call<MiningInformationModel>

    @GET()
    fun getNanoPoolComputation(@Url string: String): Call<CalculationResultModel>

    @GET("0x78bb475e0275545a779bff1a1f8c02f0c51f8a15")
    fun checkEthAddress(): Call<AddressValidationModel>
}