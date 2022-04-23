package com.talktomii.ui.homefrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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

    private var isShowMore = false

    class ViewHolder(val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPopular.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPopularBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        //isShowMore -> False -> Show All Items
        return if (isShowMore)
            popularArrayList.size
        else if (!isShowMore)
            if (popularArrayList.size > 10)
                10
            else popularArrayList.size
        else popularArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (popularArrayList[position].fname == null) {
            holder.binding.txtName.text = ""
        } else {
            holder.binding.txtName.text =
                popularArrayList[position].fname + " " + popularArrayList[position].lname
        }

        if (popularArrayList[position].price != null && popularArrayList[position].price.isNotEmpty()) {
            holder.binding.tvPriceWithTime.visibility = View.VISIBLE
            holder.binding.tvPriceWithTime.text =
                "$" + popularArrayList[position].price[0].price + "/" + popularArrayList[position].price[0].time + "min"
        } else {
            holder.binding.tvPriceWithTime.visibility = View.INVISIBLE
        }
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

        holder.binding.tvAboutMee.setOnClickListener {
            listener.onViewPopularClick(popularArrayList[position])
        }
        holder.binding.constrainItemListing.setOnClickListener {
            listener.onViewPopularClick(popularArrayList[position])
        }

    }

    fun setPopularList(admin: ArrayList<Admin>) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            admin.sortWith(
//                Comparator.comparing(Admin::lname)
//                    .thenComparing(Admin::fname)
//            )
//        } else {
//            admin.sortBy { it.fname.lowercase() }
//        }
        popularArrayList.addAll(admin)
        notifyDataSetChanged()
    }


    fun getList(): ArrayList<Admin> {
        return popularArrayList
    }


    fun showMoreOrLess(showMoreOrLess: Boolean) {
        isShowMore = showMoreOrLess
    }

    interface onViewPopularClick {
        fun onViewPopularClick(admin: Admin)
    }
}