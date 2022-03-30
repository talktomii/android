package com.talktomii.ui.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ItemScheduledAppointmentBinding

class AdapterScheduledAppointment : RecyclerView.Adapter<AdapterScheduledAppointment.ViewHolder>(){


    class ViewHolder(val binding: ItemScheduledAppointmentBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterScheduledAppointment.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduledAppointmentBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}