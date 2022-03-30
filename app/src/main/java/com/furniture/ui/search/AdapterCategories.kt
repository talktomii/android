package com.furniture.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furniture.data.model.Interest
import com.furniture.databinding.ItemCategoriesBinding

class AdapterCategories(private var context: Context) :
    RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    private var interestArrayList: ArrayList<Interest> = ArrayList()

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
        this.interestArrayList = interest
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (position % 3 == 0) {
//            holder.binding.iVImage.setImageDrawable(context.resources.getDrawable(R.drawable.ic_car1))
//        }

        holder.binding.txtStatus.text = interestArrayList[position].name
        Glide.with(context).load(interestArrayList[position].image).into(holder.binding.iVImage)
    }
}