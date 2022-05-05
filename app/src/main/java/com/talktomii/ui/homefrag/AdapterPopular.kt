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
import java.util.*

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

        if (popularArrayList[position].isOnline) {
            holder.binding.ivUserStatus.visibility = View.VISIBLE
        } else {
            holder.binding.ivUserStatus.visibility = View.GONE
        }
        holder.binding.textView6.text = popularArrayList[position].userName
        Glide.with(context).load(popularArrayList[position].coverPhoto)
            .placeholder(R.drawable.ic_image1).error(R.drawable.ic_image1)
            .into(holder.binding.ivCoverPhoto)

        Glide.with(context).load(popularArrayList[position].profilePhoto)
            .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
            .into(holder.binding.imgDefault)

        holder.binding.ivCall.setOnClickListener {
            listener.onViewPopularClick(popularArrayList[position],1)
        }

        holder.binding.tvAboutMee.setOnClickListener {
           listener.onViewPopularClick(popularArrayList[position], 2)
        }
        holder.binding.constrainItemListing.setOnClickListener {
            listener.onViewPopularClick(popularArrayList[position], 1)
        }

    }

    fun setPopularList(admin: ArrayList<Admin>) {
//        admin.sortBy { it.fname.lowercase() }
        Collections.sort(admin,
            Comparator<Admin> { receivedSOSModel, t1 ->
                if (receivedSOSModel == null || receivedSOSModel.fname == null) {
                    return@Comparator -1 //null values will be displayed at bottom in sorted list
                }
                if (t1 == null || t1.fname == null) {
                    return@Comparator 1 //null values will be displayed at bottom in sorted list
                }
                receivedSOSModel.fname.compareTo(t1.fname)
            })
        if (popularArrayList.isNotEmpty()) {
            popularArrayList.clear()
        }
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
        fun onViewPopularClick(admin: Admin, which: Int)
    }

}