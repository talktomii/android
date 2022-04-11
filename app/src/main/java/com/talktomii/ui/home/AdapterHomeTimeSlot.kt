package com.talktomii.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.databinding.ItemTimeSlotBinding
import java.text.SimpleDateFormat

class AdapterHomeTimeSlot(timeStop: TimeStop, private var listener: onViewItemClick) : RecyclerView.Adapter<AdapterHomeTimeSlot.ViewHolder>() {

    var timeStop: TimeStop

    init {
        this.timeStop = timeStop
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHomeTimeSlot.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeSlotBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var text = timeStop.slot[position]
        holder.binding.tvTime.text = SimpleDateFormat("hh:mm a").format(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(
                text
            )
        )
        holder.binding.tvTime.setOnClickListener {
            listener.onViewItemTimeSelect(text)
        }
        holder.binding.tvTime.isSelected = timeStop.isSelected
    }

    override fun getItemCount(): Int {
        return timeStop.slot.size
    }

    class ViewHolder(val binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root)
    interface onViewItemClick{
        public fun onViewItemTimeSelect(text: String)
    }
}