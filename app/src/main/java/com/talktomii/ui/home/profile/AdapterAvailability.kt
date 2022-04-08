package com.talktomii.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin.Interest
import com.talktomii.databinding.ItemAvailabilityBinding

class AdapterAvailability() : RecyclerView.Adapter<AdapterAvailability.ViewHolder>() {

    private var arraylist: ArrayList<Availaibility> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAvailabilityBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemAvailabilityBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    fun setItemList(list: ArrayList<Availaibility>) {
        arraylist.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = arraylist.get(position)
        holder.binding.txtTime.text = model.startTime + "-" + model.endTime
        holder.binding.day.text = model.end
    }
}