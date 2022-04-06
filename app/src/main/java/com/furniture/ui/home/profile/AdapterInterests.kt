package com.furniture.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.data.model.admin.Interest
import com.furniture.databinding.ItemInterestsBinding

class AdapterInterests() : RecyclerView.Adapter<AdapterInterests.ViewHolder>() {

    private var arrayList: ArrayList<Interest> = arrayListOf()

    class ViewHolder(val binding: ItemInterestsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInterests.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInterestsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemName.text = arrayList[position].name
    }

    fun setItemList(interest: ArrayList<Interest>) {
        arrayList.addAll(interest)
        notifyDataSetChanged()
    }
}