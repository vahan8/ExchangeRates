package com.vahan.exchangerates.domain.model

data class BankExchangeRate(
    val organizationId: String,
    val bankName: String,
    val currencyExchangeRates: Map<String, CurrencyExchangeRate>
)