package com.vahan.exchangerates.ui.rates

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vahan.exchangerates.R
import com.vahan.exchangerates.domain.model.Currency
import com.vahan.exchangerates.domain.model.PaymentType
import com.vahan.exchangerates.domain.model.BankExchangeRate

class RatesAdapter(private val context: Context, var currency: Currency, var paymentType: PaymentType, var rates: MutableList<BankExchangeRate>,
                   private val onClicked: (BankExchangeRate) -> Unit) : RecyclerView.Adapter<RatesAdapter.CardViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_rate, parent, false)
        return CardViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val bankRate = rates[position]
        val currencyRate = bankRate.currencyExchangeRates.get(currency.getCode())
        val rate = if (paymentType == PaymentType.CASH) currencyRate!!.cashExchangeRate else currencyRate!!.nonCashExchangeRate

        holder.name.text = bankRate.bankName
        holder.buy.text = rate.buy.toString()
        holder.sell.text = rate.sell.toString()
    }

    fun swap(rates: List<BankExchangeRate>) {
        this.rates.clear()
        this.rates.addAll(rates)
        notifyDataSetChanged()
    }

    fun updateCurrency(currency: Currency) {
        this.currency = currency
        notifyDataSetChanged()
    }

    fun updatePaymentType(paymentType: PaymentType) {
        this.paymentType = paymentType
        notifyDataSetChanged()
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textview_bank_name)
        val buy: TextView = view.findViewById(R.id.textview_rate_buy)
        val sell: TextView = view.findViewById(R.id.textview_rate_sell)

        init {
            view.setOnClickListener {
                val item = rates.get(layoutPosition)
                onClicked(item)
            }
        }
    }
}