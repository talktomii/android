package com.furniture.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.databinding.ItemTimeSlotBinding

class AdapterTimeSlot : RecyclerView.Adapter<AdapterTimeSlot.ViewHolder>(){


    class ViewHolder(val binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTimeSlot.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeSlotBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}