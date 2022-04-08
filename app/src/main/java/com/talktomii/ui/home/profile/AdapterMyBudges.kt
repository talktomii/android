package com.talktomii.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ItemMyBudgesBinding

class AdapterMyBudges : RecyclerView.Adapter<AdapterMyBudges.ViewHolder>(){


    class ViewHolder(val binding: ItemMyBudgesBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMyBudges.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMyBudgesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}