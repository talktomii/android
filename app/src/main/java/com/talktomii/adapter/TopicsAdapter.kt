package com.talktomii.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.ItemInterestsBinding
import com.talktomii.databinding.PreferredTopicsItemBinding
import com.talktomii.recycleradapter.AbstractModel
import com.talktomii.ui.tellusmore.ItemsViewModel
import com.talktomii.ui.tellusmore.TellUsMore

class TopicsAdapter(private val mList: List<ItemsViewModel>,var fragment: TellUsMore) :
    RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {


    class ViewHolder(val binding: PreferredTopicsItemBinding) : RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PreferredTopicsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.binding.tvItemName.text = itemsViewModel.name
        if(itemsViewModel.isSelected){
            holder.binding.clItems.background=holder.binding.root.context.resources.getDrawable(R.drawable.curve_background_blue)
            holder.binding.tvItemName.setTextColor( holder.binding.root.context.resources.getColor(R.color.white))
            fragment.dataChanged(mList[position])
        }else{
            holder.binding.clItems.background=holder.binding.root.context.resources.getDrawable(R.drawable.curve_background)
            holder.binding.tvItemName.setTextColor( holder.binding.root.context.resources.getColor(R.color.grey_text))
            fragment.dataRemoved(mList[position])
        }
        holder.binding.root.setOnClickListener {
            mList[position].isSelected=!mList[position].isSelected

            notifyDataSetChanged()
        }

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    data class ItemsViewModel(
        val __v: Int,
        val _id: String,
        val createdAt: String,
        val description: String,
        val image: String,
        val name: String,
        var isSelected: Boolean=false,
        val updatedAt: String
    ) : AbstractModel()

}

