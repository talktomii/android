package com.furniture.ui.home.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.data.model.admin.Interest
import com.furniture.databinding.ItemInterestsBinding

class AdapterInterests(private var context: Context) :
    RecyclerView.Adapter<AdapterInterests.ViewHolder>() {


    private var interestArrayList: ArrayList<Interest> = arrayListOf()

    class ViewHolder(val binding: ItemInterestsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInterests.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInterestsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return interestArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemName.text = interestArrayList[position].name

    }

    fun setItemList(interest: ArrayList<Interest>) {
        interestArrayList.addAll(interest)
        notifyDataSetChanged()
    }
}