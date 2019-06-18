package com.vahan.exchangerates.domain.model

enum class PaymentType(private val code: String, private val type: String) {
    CASH("0", "CASH"),
    NON_CASH("1", "NON CASH");

    fun getCode(): String = code

    fun getType(): String = type

    companion object {
        private val map: Map<String, PaymentType> = values().associateBy(PaymentType::getType)

        private val DEFAULT = CASH

        fun fromType(value: String): PaymentType = map[value] ?: DEFAULT
    }
}