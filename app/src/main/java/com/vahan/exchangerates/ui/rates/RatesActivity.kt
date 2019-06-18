package com.vahan.exchangerates.ui.rates

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vahan.exchangerates.R
import com.vahan.exchangerates.domain.model.Currency
import com.vahan.exchangerates.domain.model.PaymentType
import com.vahan.exchangerates.ui.bankinfo.BankInfoActivity
import com.vahan.exchangerates.ui.util.ActivityKeys

class RatesActivity : AppCompatActivity() {
    private lateinit var viewModel: RatesViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var layoutContent: View
    private lateinit var layoutEmpty: View
    private lateinit var layoutLoading: View

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RatesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        val currencySpinner: Spinner = findViewById<Spinner>(R.id.spinner_currency)
        currencySpinner.apply {
            val items: List<String> = Currency.values().map { it.getCode()};
            val adapter = ArrayAdapter(this@RatesActivity, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            setAdapter(adapter)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val code = adapter.getItem(position)
                    recyclerViewAdapter.updateCurrency(Currency.fromCode(code))
                }
            }
        }

        val paymentTypeSpinner: Spinner = findViewById(R.id.spinner_payment_type)
        paymentTypeSpinner.apply {
            val items: List<String> = PaymentType.values().map { it.getType()};
            val adapter = ArrayAdapter(this@RatesActivity, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            setAdapter(adapter)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val type = adapter.getItem(position)
                    recyclerViewAdapter.updatePaymentType(PaymentType.fromType(type))
                }
            }
        }

        swipeRefreshLayout = findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadData() }

        layoutLoading = findViewById(R.id.layout_loading)
        layoutContent = findViewById(R.id.layout_content)
        layoutEmpty = findViewById(R.id.layout_empty)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@RatesActivity)
            this.itemAnimator = DefaultItemAnimator()
            val currency = Currency.fromCode(currencySpinner.selectedItem as String)
            val paymentType  = PaymentType.fromType(currencySpinner.selectedItem as String)
            recyclerViewAdapter = RatesAdapter(this@RatesActivity, currency, paymentType, mutableListOf()){
                val intent = Intent(this@RatesActivity, BankInfoActivity::class.java)
                intent.putExtra(ActivityKeys.KEY_ORGANIZATION_ID, it.organizationId)
                intent.putExtra(ActivityKeys.KEY_BANK_NAME, it.bankName)
                startActivity(intent)
            }
            this.adapter = recyclerViewAdapter
        }

        viewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)
        viewModel.getRatesObservable().observe(this, Observer { result ->
            recyclerViewAdapter.swap(result)
            swipeRefreshLayout.isRefreshing = false
            setContentVisible(true)
        })
        viewModel.loadData()

        setContentVisible(false)
    }

    private fun setContentVisible(dataAvailable: Boolean) {
        layoutLoading.visibility = if (dataAvailable) View.GONE else View.VISIBLE
        layoutContent.visibility = if (dataAvailable) View.VISIBLE else View.GONE
        if (dataAvailable){
            layoutLoading.visibility = View.GONE
            layoutContent.visibility = if(recyclerViewAdapter.rates.isEmpty()) View.GONE else View.VISIBLE
            layoutEmpty.visibility = if(recyclerViewAdapter.rates.isEmpty()) View.VISIBLE else View.GONE
        } else {
            layoutLoading.visibility = View.VISIBLE
            layoutContent.visibility = View.GONE
            layoutEmpty.visibility = View.GONE
        }
    }
}
