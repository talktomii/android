package com.talktomii.ui.mywallet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.ui.mywallet.models.WalletExpensesItemModel


class WalletExpensesAdapter(private val mList: List<WalletExpensesItemModel>) :
    RecyclerView.Adapter<WalletExpensesAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wallet_expense_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.itemName.text = itemsViewModel.wallet_name
        holder.itemDate.text = itemsViewModel.wallet_date
        holder.itemPrice.text = itemsViewModel.wallet_price
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvWalletRefilltext)
        val itemDate : TextView = itemView.findViewById(R.id.tvWalletRefillDate)
        val itemPrice : TextView = itemView.findViewById(R.id.tvWalletRefillPrice)
        val payItems : ConstraintLayout = itemView.findViewById(R.id.walletRefillItems)

    }

}