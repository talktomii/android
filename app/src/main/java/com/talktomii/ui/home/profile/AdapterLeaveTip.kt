package com.talktomii.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ItemLeaveTipBinding

class AdapterLeaveTip : RecyclerView.Adapter<AdapterLeaveTip.ViewHolder>(){


    class ViewHolder(val binding: ItemLeaveTipBinding) : RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterLeaveTip.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLeaveTipBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return 5
    }

 override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}