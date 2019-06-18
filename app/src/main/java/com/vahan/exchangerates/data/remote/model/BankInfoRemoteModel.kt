package com.vahan.exchangerates.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankInfoRemoteModel (
    @Expose @SerializedName("list") val branchesMap: Map<String, BankBranchRemoteModel>
)