package com.vahan.exchangerates.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankExchangeRateRemoteModel (
    @Expose @SerializedName("title") val title: String,
    @Expose @SerializedName("list") val currencyExchangeRates: Map<String,Map<String,ExchangeRateRemoteModel>>
)