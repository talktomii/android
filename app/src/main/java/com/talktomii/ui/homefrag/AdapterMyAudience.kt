package com.talktomii.ui.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ItemMyAudienceBinding

class AdapterMyAudience  : RecyclerView.Adapter<AdapterMyAudience.ViewHolder>(){


    class ViewHolder(val binding: ItemMyAudienceBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMyAudience.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMyAudienceBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}