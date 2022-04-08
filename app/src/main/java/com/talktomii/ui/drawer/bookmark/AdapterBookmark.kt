package com.talktomii.ui.drawer.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.model.drawer.bookmark.Service
import com.talktomii.databinding.ItemPopularBinding

class AdapterBookmark(
    private var context: Context,
    private val popularArrayList: ArrayList<Service>,
    var listener: onClickInteface
) :
    RecyclerView.Adapter<AdapterBookmark.ViewHolder>() {

    class ViewHolder(val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBookmark.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPopularBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return popularArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtName.text = popularArrayList[position].uid.name
        holder.binding.textView6.text = popularArrayList[position].uid.userName
        Glide.with(context).load(popularArrayList[position].uid.coverPhoto)
            .into(holder.binding.ivCoverPhoto)

        Glide.with(context).load(popularArrayList[position].uid.profilePhoto)
            .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
            .into(holder.binding.imgDefault)

        holder.binding.ivCall.setOnClickListener {
            listener.onClick(popularArrayList[position])
        }

    }

    fun setPopularList(admin: List<Service>) {
        popularArrayList.addAll(admin)
        notifyDataSetChanged()
    }

    interface onClickInteface {
        fun onClick(admin: Service)
    }

}