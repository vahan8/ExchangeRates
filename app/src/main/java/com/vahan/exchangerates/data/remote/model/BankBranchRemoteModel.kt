package com.vahan.exchangerates.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankBranchRemoteModel (
    @Expose @SerializedName("head") val head: Int,
    @Expose @SerializedName("title") val title: StringWithTranslationRemoteModel,
    @Expose @SerializedName("address") val address: StringWithTranslationRemoteModel
)