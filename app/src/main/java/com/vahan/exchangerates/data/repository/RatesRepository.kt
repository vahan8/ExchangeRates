package com.vahan.exchangerates.data.repository

import com.vahan.exchangerates.data.remote.ApiClient
import com.vahan.exchangerates.data.remote.OnResponse
import com.vahan.exchangerates.domain.model.BankExchangeRate

object RatesRepository {
    // In real life project it is better to declare interfaces for remote data source and pass them to repository not to depend on implementation details
    fun getRatesAsync(onResponse: OnResponse<List<BankExchangeRate>>) {
        ApiClient.getRatesAsync(onResponse)
    }
}