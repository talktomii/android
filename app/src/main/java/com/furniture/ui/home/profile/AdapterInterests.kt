package com.furniture.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.databinding.ItemInterestsBinding

class AdapterInterests : RecyclerView.Adapter<AdapterInterests.ViewHolder>(){


    class ViewHolder(val binding: ItemInterestsBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInterests.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInterestsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}