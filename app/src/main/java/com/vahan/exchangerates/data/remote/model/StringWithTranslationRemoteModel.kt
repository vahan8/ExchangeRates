package com.vahan.exchangerates.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StringWithTranslationRemoteModel (
    @Expose @SerializedName("en") val english: String,
    @Expose @SerializedName("am") val armenian: String,
    @Expose @SerializedName("ru") val russian: String
)