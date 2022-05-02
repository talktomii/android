package com.talktomii.ui.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R

class AdapterRescheduleTimeSlot() : RecyclerView.Adapter<AdapterRescheduleTimeSlot.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRescheduleTimeSlot.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_time_slot2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemName.text = SimpleDateFormat("hh:mm a").format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(""))
    }

    override fun getItemCount(): Int {
        return 8
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvRescheduleTime)
    }
}