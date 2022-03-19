package com.furniture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.ui.mycards.CardItemsViewModel

class MyCardAdapter(private val mList: List<CardItemsViewModel>) :
    RecyclerView.Adapter<MyCardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mycards_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.itemName.text = itemsViewModel.card_Number
        holder.itemImg.setImageResource(itemsViewModel.card_Img)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvcardNumber)
        val itemImg : ImageView = itemView.findViewById(R.id.tvCardImage)

    }

}