package com.talktomii.ui.drawer.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.model.drawer.bookmark.Service
import com.talktomii.databinding.ItemPopularBinding
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.isUser
import javax.inject.Inject

class AdapterBookmark(
    private var context: Context,
    private val popularArrayList: ArrayList<Service>,
    var listener: onClickInteface,
    private var isUser: Boolean
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
        if (isUser) {
            holder.binding.txtName.text =
                popularArrayList[position].ifid.fname + " " + popularArrayList[position].ifid.lname
            holder.binding.textView6.text = popularArrayList[position].ifid.userName

            Glide.with(context).load(popularArrayList[position].ifid.coverPhoto)
                .placeholder(R.drawable.ic_image1).error(R.drawable.ic_image1)
                .into(holder.binding.ivCoverPhoto)

            Glide.with(context).load(popularArrayList[position].ifid.profilePhoto)
                .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                .into(holder.binding.imgDefault)
        } else {
            holder.binding.txtName.text =
                popularArrayList[position].uid.fname + " " + popularArrayList[position].uid.lname
            holder.binding.textView6.text = popularArrayList[position].uid.userName

            Glide.with(context).load(popularArrayList[position].uid.coverPhoto)
                .placeholder(R.drawable.ic_image1).error(R.drawable.ic_image1)
                .into(holder.binding.ivCoverPhoto)

            Glide.with(context).load(popularArrayList[position].uid.profilePhoto)
                .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                .into(holder.binding.imgDefault)
        }

        holder.binding.ivCall.setOnClickListener {
            listener.onClick(popularArrayList[position])
        }

        holder.binding.tvAboutMee.setOnClickListener {
            listener.onClick(popularArrayList[position])
        }

    }

    fun setPopularList(admin: List<Service>) {
        popularArrayList.clear()
        popularArrayList.addAll(admin)
        notifyDataSetChanged()
    }

    interface onClickInteface {
        fun onClick(admin: Service)
    }

}