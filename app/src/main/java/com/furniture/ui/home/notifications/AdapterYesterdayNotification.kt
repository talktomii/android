package com.furniture.ui.home.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.databinding.ItemYesterdayNotificationBinding

class AdapterYesterdayNotification: RecyclerView.Adapter<AdapterYesterdayNotification.ViewHolder>(){


    class ViewHolder(val binding: ItemYesterdayNotificationBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterYesterdayNotification.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemYesterdayNotificationBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}