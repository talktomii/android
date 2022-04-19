package com.talktomii.ui.homefrag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.model.admin.Admin
import com.talktomii.databinding.ItemPopularBinding

class AdapterPopular(
    private var context: Context,
    private val popularArrayList: ArrayList<Admin>,
    var listener: onViewPopularClick
) :
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
        holder.binding.textView6.text = popularArrayList[position].userName
        Glide.with(context).load(popularArrayList[position].coverPhoto)
            .placeholder(R.drawable.ic_image1).error(R.drawable.ic_image1)
            .into(holder.binding.ivCoverPhoto)

        Glide.with(context).load(popularArrayList[position].profilePhoto)
            .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
            .into(holder.binding.imgDefault)

        holder.binding.ivCall.setOnClickListener {
            listener.onViewPopularClick(popularArrayList[position])
        }

    }

    fun setPopularList(admin: ArrayList<Admin>) {
        popularArrayList.addAll(admin)
        notifyDataSetChanged()
    }

    interface onViewPopularClick {
        fun onViewPopularClick(admin: Admin)
    }
}