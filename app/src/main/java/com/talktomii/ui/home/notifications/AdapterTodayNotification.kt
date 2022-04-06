package com.talktomii.ui.home.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ItemTodayNotificationBinding

class AdapterTodayNotification : RecyclerView.Adapter<AdapterTodayNotification.ViewHolder>(){


    class ViewHolder(val binding: ItemTodayNotificationBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTodayNotification.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodayNotificationBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}