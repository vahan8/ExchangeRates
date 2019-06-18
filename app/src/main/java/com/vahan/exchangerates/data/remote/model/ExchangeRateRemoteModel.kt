package com.vahan.exchangerates.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExchangeRateRemoteModel (
    @Expose @SerializedName("buy") val buy: Double,
    @Expose @SerializedName("sell") val sell: Double
)