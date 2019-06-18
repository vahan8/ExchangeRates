package com.vahan.exchangerates.ui.bankinfo

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.vahan.exchangerates.R
import com.vahan.exchangerates.domain.model.BankBranch
import com.vahan.exchangerates.ui.util.ActivityKeys

class BankInfoActivity : AppCompatActivity() {
    private lateinit var viewModel: BankBranchesViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: BankBranchesAdapter
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_info)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val organizationId: String = intent.extras.getString(ActivityKeys.KEY_ORGANIZATION_ID)!!
        val bankName: String = intent.extras.getString(ActivityKeys.KEY_BANK_NAME)!!
        title = getString(R.string.bank_info_activity_title)

        //  val textViewBankName = findViewById<TextView>(R.id.textview_bank_name)
        // textViewBankName.text = organizationId

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@BankInfoActivity)
            this.itemAnimator = DefaultItemAnimator()
            recyclerViewAdapter = BankBranchesAdapter(this@BankInfoActivity, mutableListOf()) {
                setSelectedBrancInfo(it)
            }
            this.adapter = recyclerViewAdapter
        }

        val factory = BankBranchesViewModel.Factory(organizationId)
        viewModel = ViewModelProviders.of(this, factory).get(BankBranchesViewModel::class.java)
        viewModel.getBankBranchesObservable().observe(this, Observer { result ->
            recyclerViewAdapter.swap(result)
            setSelectedBrancInfo((result.firstOrNull() { it.head }) ?: result[0])
        })
        viewModel.loadData()

        collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_bank_info)
        collapsingToolbarLayout.title = bankName
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setSelectedBrancInfo(selectedBranch: BankBranch) {
        val textViewHead = findViewById<TextView>(R.id.textview_branch_title)
        textViewHead.text = selectedBranch.title
        val textViewAddress = findViewById<TextView>(R.id.textview_address)
        textViewAddress.text = selectedBranch.address
    }
}
