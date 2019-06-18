package com.vahan.exchangerates.domain.model

data class CurrencyExchangeRate (
    val cashExchangeRate: ExchangeRate,
    val nonCashExchangeRate: ExchangeRate
)