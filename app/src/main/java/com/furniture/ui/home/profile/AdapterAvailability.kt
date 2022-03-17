package com.furniture.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.databinding.ItemAvailabilityBinding

class AdapterAvailability: RecyclerView.Adapter<AdapterAvailability.ViewHolder>(){


    class ViewHolder(val binding: ItemAvailabilityBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAvailability.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAvailabilityBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}