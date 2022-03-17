package com.furniture.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.ui.tellusmore.ItemsViewModel

class TopicsAdapter(private val mList: List<ItemsViewModel>) :
    RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {
    private var onItemClick: OnItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.preferred_topics_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.itemName.text = itemsViewModel.item

    }

    fun setOnItemClick(onItemClick: OnItemClick?) {
        this.onItemClick = onItemClick
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvItemName)


        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                itemName.setBackgroundColor(itemView.context.resources.getColor(R.color.blue_check))
                itemName.setTextColor(itemView.context.resources.getColor(R.color.white))

            }
        }

    }


    fun interface OnItemClick {
        fun onClick(view: View)
    }
}

