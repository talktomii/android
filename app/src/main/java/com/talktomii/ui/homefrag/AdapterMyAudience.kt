package com.talktomii.ui.homefrag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.model.admin1.UsersDataItem
import com.talktomii.databinding.ItemMyAudienceBinding

class AdapterMyAudience(private var context: Context) :
    RecyclerView.Adapter<AdapterMyAudience.ViewHolder>() {

    private var list: ArrayList<UsersDataItem> = arrayListOf()

    class ViewHolder(val binding: ItemMyAudienceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterMyAudience.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMyAudienceBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = list[position]
        Glide.with(context).load(model.coverPhoto).error(R.drawable.ic_user)
            .placeholder(R.drawable.ic_user).into(holder.binding.ivImage)
        if (model.name.isNullOrBlank()) {
            holder.binding.txtName.text = model.name
        } else {
            holder.binding.txtName.text = model.fname + " " + model.lname
        }
        holder.binding.txtUserNAme.text = model.userName
    }

    fun setList(list: ArrayList<UsersDataItem>) {
        this.list = list
        notifyDataSetChanged()
    }

}