package com.vahan.exchangerates.domain.model

enum class Currency(private val code: String) {
    USD("USD"),
    EUR("EUR"),
    RUR("RUR");

    fun getCode(): String = code

    companion object {
        private val map: Map<String, Currency> = values().associateBy(Currency::getCode)

        private val DEFAULT = USD

        fun fromCode(value: String): Currency = map[value] ?: DEFAULT
    }
}