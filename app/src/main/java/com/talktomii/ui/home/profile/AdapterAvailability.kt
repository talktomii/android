package com.talktomii.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.databinding.ItemAvailabilityBinding
import com.talktomii.utlis.DateUtils.getDateToShortDate
import com.talktomii.utlis.DateUtils.setDateToTime

class AdapterAvailability(onEditInterface: OnEditInterface) :
    RecyclerView.Adapter<AdapterAvailability.ViewHolder>() {

    private var arraylist: ArrayList<Availaibility> = arrayListOf()
    private var onEditInterface: OnEditInterface

    init {
        this.onEditInterface = onEditInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAvailabilityBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemAvailabilityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return arraylist.size
    }

    fun setItemList(list: ArrayList<Availaibility>) {
        if (arraylist.isNotEmpty()) {
            arraylist.clear()
        }
        arraylist.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = arraylist.get(position)
        holder.binding.txtTime.text =
            setDateToTime(model.startTime) + "-" + setDateToTime(model.endTime)

        var day: String = ""
        for (item in model.day) {
            if (item.equals("1")) {
                day = day + "Mon"
            } else if (item.equals("2")) {
                if (day.isEmpty()) {
                    day = day + "Tue"
                } else {
                    day = day + "-Tue"
                }
            } else if (item.equals("3")) {
                if (day.isEmpty()) {
                    day = day + "Wed"
                } else {
                    day = day + "-Wed"
                }
            } else if (item.equals("4")) {
                if (day.isEmpty()) {
                    day = day + "Thu"
                } else {
                    day = day + "-Thu"
                }
            } else if (item.equals("5")) {
                if (day.isEmpty()) {
                    day = day + "Fri"
                } else {
                    day = day + "-Fri"
                }
            } else if (item.equals("6")) {
                if (day.isEmpty()) {
                    day = day + "Sat"
                } else {
                    day = day + "-Sat"
                }
            } else if (item.equals("7")) {
                if (day.isEmpty()) {
                    day = day + "Sun"
                } else {
                    day = day + "-Sun"
                }
            }
        }
        holder.binding.day.text = day
        if (model.end != null) {
            holder.binding.end.text = getDateToShortDate(model.end)
        } else {
            holder.binding.end.text = model.end
        }

        holder.binding.ivEdit.setOnClickListener {
            onEditInterface.onEdit(model, position)
        }

    }

    interface OnEditInterface {
        fun onEdit(model: Availaibility, position: Int)
    }

}