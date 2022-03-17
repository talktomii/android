package com.furniture.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.databinding.ItemCategoriesBinding

class AdapterCategories(var context: Context): RecyclerView.Adapter<AdapterCategories.ViewHolder>(){


    class ViewHolder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategories.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 15
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position%3==0){
            holder.binding.iVImage.setImageDrawable(context.resources.getDrawable(R.drawable.ic_splash))
        }
    }
}