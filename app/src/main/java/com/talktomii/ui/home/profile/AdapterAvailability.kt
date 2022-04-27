package com.talktomii.ui.home.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.databinding.ItemAvailabilityBinding
import com.talktomii.utlis.DateUtils.getDateToShortDate
import com.talktomii.utlis.DateUtils.setDateToTime
import com.talktomii.utlis.DateUtils.setDateToTimeUTCToLocal
import java.util.*

class AdapterAvailability(private var context: Context, onEditInterface: OnEditInterface) :
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

    fun deleteItem(position: Int) {
        arraylist.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = arraylist.get(position)
        holder.binding.txtTime.text =
            setDateToTimeUTCToLocal(model.startTime) + "-" + setDateToTimeUTCToLocal(model.endTime)

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

            val popupMenu = PopupMenu(context, it)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.avaibility_popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(object :
                android.widget.PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    when (p0!!.itemId) {
                        R.id.action_adelete -> {

                            onEditInterface.onDelete(model, position, 2)
                        }
                        R.id.action_rescedule -> {
                            onEditInterface.onEdit(model, position, 1)
                        }
                    }
                    return false
                }
            })
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    popupMenu.gravity = Gravity.END
//                }
            popupMenu.show()
        }

    }

    interface OnEditInterface {
        fun onEdit(model: Availaibility, position: Int, i: Int)
        fun onDelete(model: Availaibility, position: Int, i: Int)
    }

}