package com.talktomii.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.ui.mycards.PaymentItemsViewModel
import com.talktomii.ui.mycards.activities.PaymentDetailsActivity

class MyPaymentAdapter(private val mList: List<PaymentItemsViewModel>) :
    RecyclerView.Adapter<MyPaymentAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mypayments_item, parent, false)
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
            intent.putExtra("id",mList[position].wallet_id)
            intent.putExtra("date",mList[position].wallet_date)
            intent.putExtra("type",mList[position].wallet_name)
            intent.putExtra("amount",mList[position].wallet_price)
            context!!.startActivity(intent)
        }
        when (context!!.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                holder.payImg.setBackgroundResource(R.drawable.walleticon_dark)
                holder.payImg.maxWidth = 48
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                holder.payImg.setBackgroundResource(R.drawable.walleticon)
                holder.payImg.maxWidth = 48
            }
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
        val payImg : ImageView = itemView.findViewById(R.id.walletImage)

    }

}