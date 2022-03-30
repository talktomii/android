package com.talktomii.ui.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ItemNearestAppointmentsBinding

class AdapterNearestAppointment  : RecyclerView.Adapter<AdapterNearestAppointment.ViewHolder>(){


    class ViewHolder(val binding: ItemNearestAppointmentsBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNearestAppointment.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNearestAppointmentsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}