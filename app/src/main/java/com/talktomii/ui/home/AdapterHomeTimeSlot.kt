package com.talktomii.ui.home

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.model.getallslotbydate.TimeSlotsWithData
import com.talktomii.databinding.ItemTimeSlotBinding
import com.talktomii.utlis.DateFormate.TIME_FORMAT
import com.talktomii.utlis.DateUtils
import com.talktomii.utlis.DateUtils.checkPastTime
import com.talktomii.utlis.DateUtils.convertShortDateFormat
import com.talktomii.utlis.DateUtils.getCurrentDateFormatted
import com.talktomii.utlis.DateUtils.getSubStringTime
import com.talktomii.utlis.DateUtils.setDateToTimeUTCToLocal
import java.text.SimpleDateFormat
import java.util.*

class AdapterHomeTimeSlot(
    private var context: Context,
    private var arrayList: java.util.ArrayList<TimeSlotsWithData>,
    private var listener: onViewItemClick
) :
    RecyclerView.Adapter<AdapterHomeTimeSlot.ViewHolder>() {
    var lastPosition: Int = -1
    private var selectedDate: String = ""

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
        holder.binding.tvTime.text = setDateToTimeUTCToLocal(text)
        if (lastPosition == position) {
            holder.binding.tvTime.background =
                ContextCompat.getDrawable(context, R.drawable.back_select)
        } else {
            when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    holder.binding.tvTime.background =
                        ContextCompat.getDrawable(context, R.drawable.back_tabs_dark)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    holder.binding.tvTime.background =
                        ContextCompat.getDrawable(context, R.drawable.back_tabs)
                }
            }

        }
        holder.binding.tvTime.setOnClickListener {
            val dateSelected = convertShortDateFormat(selectedDate)
            val currentDate = getCurrentDateFormatted()

            if (dateSelected!! > currentDate) {
                listener.onViewItemTimeSelect(text, "")
                timeSlotsWithData.isSelected = true
                notifyDataSetChanged()
                lastPosition = position
                arrayList[lastPosition].isSelected = false
            } else {
                val subString = DateUtils.simpleDateToUTCTOLocalDate(text).substringAfter("T")
                val time1 = getSubStringTime(selectedDate, subString)
                val time2 = SimpleDateFormat(TIME_FORMAT).format(Date())
                if (!checkPastTime(time1, time2)) {
                    listener.onViewItemTimeSelect(text, "")
                    timeSlotsWithData.isSelected = true
                    notifyDataSetChanged()
                    lastPosition = position
                    arrayList[lastPosition].isSelected = false
                } else {
                    listener.onViewItemTimeSelect(
                        "",
                        context.getString(R.string.you_can_not_schedule_time)
                    )
                }
            }
        }
    }

    fun setSelectedDate(date: String) {
        selectedDate = date
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(val binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root)
    interface onViewItemClick {
        fun onViewItemTimeSelect(text: String, message: String)
    }
}