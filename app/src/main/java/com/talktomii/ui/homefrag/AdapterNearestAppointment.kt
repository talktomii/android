package com.talktomii.ui.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.admin1.NearestAppointmentItem
import com.talktomii.databinding.ItemNearestAppointmentsBinding
import com.talktomii.utlis.DateUtils

class AdapterNearestAppointment : RecyclerView.Adapter<AdapterNearestAppointment.ViewHolder>() {

    private var interestArrayList: List<NearestAppointmentItem>? = arrayListOf()

    class ViewHolder(val binding: ItemNearestAppointmentsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterNearestAppointment.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNearestAppointmentsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return interestArrayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val interest = interestArrayList!![position]
        holder.binding.txtName.text =
            if (interest.ifid.fname != null) interest.ifid.fname else "" + " " + if (interest.ifid.lname != null) interest.ifid.lname else ""
        holder.binding.textMinutes.text = "" + interest.duration + " Minute Meeting"
        holder.binding.txtTime.text =
            DateUtils.setDateToTimeUTCToLocal(interest.startTime) + "-" + DateUtils.setDateToTimeUTCToLocal(
                interest.endTime
            )
        holder.binding.txtDate.text = DateUtils.setDateToWeekDate(interest.date)
    }

    fun setList(interest: List<NearestAppointmentItem>?) {
        this.interestArrayList = interest
        notifyDataSetChanged()
    }

}