package com.talktomii.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.model.getallslotbydate.TimeSlotsWithData
import com.talktomii.databinding.ItemTimeSlotBinding
import java.text.SimpleDateFormat

class AdapterHomeTimeSlot(
    private var context: Context,
    private var arrayList: java.util.ArrayList<TimeSlotsWithData>,
    private var listener: onViewItemClick
) :
    RecyclerView.Adapter<AdapterHomeTimeSlot.ViewHolder>() {

    var lastPosition: Int = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterHomeTimeSlot.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeSlotBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var timeSlotsWithData: TimeSlotsWithData = arrayList[position]
        var text = timeSlotsWithData.slot
        holder.binding.tvTime.text = SimpleDateFormat("hh:mm a").format(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(
                text
            )
        )
        if (lastPosition == position) {
            holder.binding.tvTime.background =
                ContextCompat.getDrawable(context, R.drawable.back_select)
        } else {
            holder.binding.tvTime.background =
                ContextCompat.getDrawable(context, R.drawable.back_tabs)
        }
        holder.binding.tvTime.setOnClickListener {
            if (SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(text).time > System.currentTimeMillis()) {
                listener.onViewItemTimeSelect(text)
                timeSlotsWithData.isSelected = true
                notifyDataSetChanged()
                lastPosition = position
                arrayList[lastPosition].isSelected = false
            } else {
                Toast.makeText(context, "Invalid Time!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(val binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root)
    interface onViewItemClick {
        fun onViewItemTimeSelect(text: String)
    }
}