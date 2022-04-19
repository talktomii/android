package com.talktomii.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.admin.Price
import com.talktomii.databinding.ItemPriceBinding

class AdapterPrice(private var listener: onViewEdiPriceClick) : RecyclerView.Adapter<AdapterPrice.ViewHolder>() {

    private var arraylist: ArrayList<Price> = arrayListOf()

    class ViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPrice.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPriceBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvPrice.text = ""+arraylist[position].price.toString()
        holder.binding.ivEditPrice.setOnClickListener {
            listener.onViewItemPriceClick()
        }
    }

    interface onViewEdiPriceClick {
        fun onViewItemPriceClick()
    }

    fun getItemListSize(): Int {
        return arraylist.size
    }

    fun getItemList(): ArrayList<Price> {
        return arraylist
    }

    fun setItemList(list: ArrayList<Price>) {
        arraylist.clear()
        arraylist.addAll(list)
        notifyDataSetChanged()
    }
}