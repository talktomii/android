package com.furniture.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.ui.mycards.CardItemsViewModel
import com.furniture.ui.mycards.PaymentItemsViewModel
import com.furniture.ui.mycards.activities.PaymentDetailsActivity
import org.w3c.dom.Text

class MyPaymentAdapter(private val mList: List<PaymentItemsViewModel>) :
    RecyclerView.Adapter<MyPaymentAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mypayments_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.itemName.text = itemsViewModel.wallet_name
        holder.itemDate.text = itemsViewModel.wallet_date
        holder.itemPrice.text = itemsViewModel.wallet_price
        holder.payItems.setOnClickListener {
            val intent = Intent(context,PaymentDetailsActivity::class.java)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvWallettext)
        val itemDate : TextView = itemView.findViewById(R.id.tvWalletDate)
        val itemPrice : TextView = itemView.findViewById(R.id.tvWalletPrice)
        val payItems : ConstraintLayout = itemView.findViewById(R.id.payItems)

    }

}