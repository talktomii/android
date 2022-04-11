package com.talktomii.ui.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.getallslotbydate.InterestItem
import com.talktomii.databinding.ItemNearestAppointmentsBinding

class AdapterNearestAppointment : RecyclerView.Adapter<AdapterNearestAppointment.ViewHolder>() {

    private var interestArrayList: List<InterestItem>? = arrayListOf()

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
        holder.binding.textMinutes.text = "" + interest.duration + "Minute Meeting"
        holder.binding.txtDate.text = interest.date
        holder.binding.txtTime.text = interest.startTime + "-" + interest.endTime
        holder.binding.txtName.text = interest.uid.name
    }

    fun setList(interest: List<InterestItem>?) {
        this.interestArrayList = interest
        notifyDataSetChanged()
    }
}