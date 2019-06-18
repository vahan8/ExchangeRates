package com.vahan.exchangerates.data.remote

import com.vahan.exchangerates.data.remote.model.BankExchangeRateRemoteModel
import com.vahan.exchangerates.data.remote.model.BankInfoRemoteModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("rates.ashx")
    fun getRates(@Query("lang") lang: String): Call<Map<String, BankExchangeRateRemoteModel>>

    @GET("branches.ashx")
    fun getBankInfo(@Query("id") organizationId: String): Call<BankInfoRemoteModel>
}


