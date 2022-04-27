package com.talktomii.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.admin1.BadgesItem
import com.talktomii.databinding.ItemMyBudgesBinding

class AdapterMyBudges : RecyclerView.Adapter<AdapterMyBudges.ViewHolder>() {


    class ViewHolder(val binding: ItemMyBudgesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMyBudges.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMyBudgesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return badgesArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val badgesItem = badgesArrayList[position]
        holder.binding.txtBudges.text = badgesItem.count.toString()
        holder.binding.tvBadgesName.text = badgesItem._id.toString()
        holder.binding.ivBadgesImage.setImageResource(badgesItem.resourse)


    }

    private var badgesArrayList: ArrayList<BadgesItem> = arrayListOf()
    fun setItemsList(badgesArrayList: ArrayList<BadgesItem>?) {
        this.badgesArrayList = badgesArrayList!!
        notifyDataSetChanged()
    }
}