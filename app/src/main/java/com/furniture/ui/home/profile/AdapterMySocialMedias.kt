package com.furniture.ui.home.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furniture.R
import com.furniture.data.model.admin.SocialNetwork
import com.furniture.databinding.ItemSocialMediaBinding

class AdapterMySocialMedias(private var context: Context) : RecyclerView.Adapter<AdapterMySocialMedias.ViewHolder>() {

    private var arraylist: ArrayList<SocialNetwork> = arrayListOf()

    class ViewHolder(val binding: ItemSocialMediaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSocialMediaBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(arraylist[position].link).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(holder.binding.ivInsta)
    }

    fun setItemList(socialNetwork: ArrayList<SocialNetwork>) {
        arraylist.addAll(socialNetwork)
        notifyDataSetChanged()
    }

}