package com.furniture.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furniture.data.model.Interest
import com.furniture.databinding.ItemCategoriesBinding
import com.furniture.interfaces.SearchItemClick

class AdapterCategories(private var context: Context, private var listener: SearchItemClick) :
    RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    var interestArrayList: ArrayList<Interest> = ArrayList()

    class ViewHolder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterCategories.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return interestArrayList.size
    }


    fun setImagesList(interest: ArrayList<Interest>) {
        this.interestArrayList.clear()
        this.interestArrayList = interest
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (position % 3 == 0) {
//            Glide.with(context).load(interestArrayList[position].image)
//                .apply(RequestOptions().override(MATCH_PARENT, 400))
//                .into(holder.binding.iVImage)
//        } else {
//            Glide.with(context).load(interestArrayList[position].image)
//                .apply(RequestOptions().override(MATCH_PARENT, 350))
//                .into(holder.binding.iVImage)
//        }
        Glide.with(context).load(interestArrayList[position].image).into(holder.binding.iVImage)
        holder.binding.txtStatus.text = interestArrayList[position].name

        holder.binding.constraintItemCategory.setOnClickListener(View.OnClickListener {
            listener.onViewSearchClick(interestArrayList[position])
        })
    }
}