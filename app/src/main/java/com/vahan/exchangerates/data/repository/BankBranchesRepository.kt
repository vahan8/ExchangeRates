package com.vahan.exchangerates.data.repository

import com.vahan.exchangerates.data.remote.ApiClient
import com.vahan.exchangerates.data.remote.OnResponse
import com.vahan.exchangerates.domain.model.BankBranch

object BankBranchesRepository {
    // In real life project it is better to declare interfaces for remote data source and pass them to repository not to depend on implementation details
    fun getBankBranchesAsync(organizationId: String, onResponse: OnResponse<List<BankBranch>>) {
        ApiClient.getBankInfoAsync(organizationId, onResponse)
    }
}