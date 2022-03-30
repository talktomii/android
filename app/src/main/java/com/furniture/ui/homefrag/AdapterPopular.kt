package com.furniture.ui.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.data.model.admin.Admin
import com.furniture.databinding.ItemPopularBinding

class AdapterPopular(var homesFragment: HomesFragment, private val popularArrayList: ArrayList<Admin>) :
    RecyclerView.Adapter<AdapterPopular.ViewHolder>() {


    class ViewHolder(val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPopular.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPopularBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return popularArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtName.text = popularArrayList[position].name
        holder.binding.txtName.text = popularArrayList[position].name
        holder.binding.ivCoverPhoto.setOnClickListener {
            homesFragment.onCoverClicked()
        }

    }

    fun setPopularList(admin: ArrayList<Admin>) {
        popularArrayList.addAll(admin)
        notifyDataSetChanged()
    }
}