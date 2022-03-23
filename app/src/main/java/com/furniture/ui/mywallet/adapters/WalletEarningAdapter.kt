package com.furniture.ui.mywallet.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.ui.mywallet.models.WalletEarningItemModel
import javax.inject.Inject

class WalletEarningAdapter(val mList: List<WalletEarningItemModel>) :
    RecyclerView.Adapter<WalletEarningAdapter.ViewHolder>() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wallet_earning_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.itemImg.setImageResource(mList[position].wallet_Img)
        holder.itemName.text = mList[position].wallet_holder_name
        holder.itemDate.text = mList[position].wallet_date
        holder.itemRs.text = mList[position].wallet_rs
        holder.itemTime.text = mList[position].wallet_time

    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemImg : ImageView = itemView.findViewById(R.id.walletEarningImg)
        val itemName: TextView = itemView.findViewById(R.id.walletEarningName)
        val itemDate: TextView = itemView.findViewById(R.id.walletearningDate)
        val itemRs : TextView = itemView.findViewById(R.id.walletearningrs)
        val itemTime : TextView = itemView.findViewById(R.id.walletEarningTime)
    }

}