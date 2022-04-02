package com.furniture.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furniture.data.model.getallslotbydate.TimeStop
import com.furniture.databinding.ItemTimeSlotBinding
import java.text.SimpleDateFormat

class AdapterTimeSlot(timeStop: TimeStop) : RecyclerView.Adapter<AdapterTimeSlot.ViewHolder>() {

    var timeStop: TimeStop

    init {
        this.timeStop = timeStop
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTimeSlot.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeSlotBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var text = timeStop.slot.get(position)
        holder.binding.tvTime.text = SimpleDateFormat("hh:mm a").format(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(
                text
            )
        )
    }

    override fun getItemCount(): Int {
        return timeStop.slot.size
    }

    class ViewHolder(val binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root)
}