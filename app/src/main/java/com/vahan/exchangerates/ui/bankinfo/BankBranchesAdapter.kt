package com.vahan.exchangerates.ui.bankinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vahan.exchangerates.R
import com.vahan.exchangerates.domain.model.BankBranch

class BankBranchesAdapter(private val context: Context, var branches: MutableList<BankBranch>, private val onClicked: (BankBranch) -> Unit) : RecyclerView.Adapter<BankBranchesAdapter.CardViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_bank_branch, parent, false)
        return CardViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return branches.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val branch = branches[position]
        holder.title.text = branch.title
    }

    fun swap(branches: List<BankBranch>) {
        this.branches.clear()
        this.branches.addAll(branches)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textview_branch_title)

        init {
            view.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val item = branches.get(layoutPosition)
                    onClicked(item)
                }
            })
        }
    }
}