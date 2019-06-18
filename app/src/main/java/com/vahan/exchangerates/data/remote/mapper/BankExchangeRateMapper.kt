package com.vahan.exchangerates.data.remote.mapper

import com.vahan.exchangerates.data.remote.model.BankExchangeRateRemoteModel
import com.vahan.exchangerates.domain.model.Currency
import com.vahan.exchangerates.domain.model.PaymentType
import com.vahan.exchangerates.domain.model.BankExchangeRate
import com.vahan.exchangerates.domain.model.CurrencyExchangeRate
import com.vahan.exchangerates.domain.model.ExchangeRate

object BankExchangeRateMapper {
    fun toLocal(organizationId: String, remote: BankExchangeRateRemoteModel): BankExchangeRate {
        val currencyExchangeRates: MutableMap<String, CurrencyExchangeRate> = mutableMapOf()

        for (currency: Currency in Currency.values()) {
            val curExRate = remote.currencyExchangeRates.get(currency.getCode())
            val cashRate = curExRate!!.get(PaymentType.CASH.getCode())
            val nonCashRate = curExRate!!.get(PaymentType.NON_CASH.getCode())

            val cashExchangeRate = ExchangeRate(
                if (cashRate == null) 0.0 else cashRate!!.buy,
                if (cashRate == null) 0.0 else cashRate!!.sell
            )
            val nonCashExchangeRate = ExchangeRate(
                if (nonCashRate == null) 0.0 else nonCashRate!!.buy,
                if (nonCashRate == null) 0.0 else nonCashRate!!.sell
            )
            currencyExchangeRates.put(currency.getCode(), CurrencyExchangeRate(cashExchangeRate, nonCashExchangeRate))
        }

        return BankExchangeRate(organizationId, remote.title, currencyExchangeRates)
    }

    fun toLocal(rates: Map<String, BankExchangeRateRemoteModel>) = rates.map { toLocal(it.key, it.value) }
}