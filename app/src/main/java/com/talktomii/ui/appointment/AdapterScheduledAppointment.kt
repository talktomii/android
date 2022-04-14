package com.talktomii.ui.appointment

import android.content.Context
import android.os.Build
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.model.appointment.AppointmentInterestItem
import com.talktomii.databinding.ItemScheduledAppointmentBinding
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.utlis.DateUtils.setDateToTime
import com.talktomii.utlis.DateUtils.setDateToWeekDate
import com.talktomii.utlis.common.Constants.Companion.CANCELLED
import javax.inject.Inject


class AdapterScheduledAppointment(
    private var context: Context,
    private var listener: onScheduleAppointment
) :
    RecyclerView.Adapter<AdapterScheduledAppointment.ViewHolder>() {

    @Inject
    lateinit var viewModel: HomeScreenViewModel
    private var interestArrayList: ArrayList<AppointmentInterestItem> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterScheduledAppointment.ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_scheduled_appointment, parent, false)
//        context = parent.context
//        webService = this.webService
//        return ViewHolder(view)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduledAppointmentBinding.inflate(layoutInflater, parent, false)
        return AdapterScheduledAppointment.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return interestArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var interest = interestArrayList[position]
        holder.binding.txtName.text = interest.ifid.fname + " " + interest.ifid.lname
        holder.binding.textMinutes.text = "" + interest.duration + " Minute Meeting"
        holder.binding.txtTime.text =
            setDateToTime(interest.startTime) + "-" + setDateToTime(interest.endTime)
        if (interest.status == CANCELLED) {
            holder.binding.ivMore.visibility = View.GONE
            holder.binding.txtCallNow.visibility = View.GONE
        } else {
            holder.binding.ivMore.visibility = View.VISIBLE
            holder.binding.txtCallNow.visibility = View.VISIBLE
        }
        holder.binding.tvDayAndDate.text =
            setDateToWeekDate(interest.date)

        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_adelete -> {
                        listener.onViewDeleteAppointment(interest, position)
                    }
                    R.id.action_rescedule -> {
                        listener.onViewRescheduleAppointment(interest, position)

                    }
                }
                return false
            }
        }
        holder.moreOptions.setOnClickListener(View.OnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.appointment_popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(moreMenuClickListener())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popupMenu.gravity = Gravity.END
            }
            popupMenu.show()
        })
    }

    fun setList(interest: ArrayList<AppointmentInterestItem>?) {
        interestArrayList = interest!!
        notifyDataSetChanged()
    }

    fun removeItemList(position: Int) {
        interestArrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItemsList(item: ArrayList<AppointmentInterestItem>?) {
        interestArrayList.clear()
        item?.let { interestArrayList.addAll(it) }
        notifyDataSetChanged()
    }

    fun clearList() {
        interestArrayList.clear()
        notifyDataSetChanged()
    }

    fun addItem(appointmentInterestItem: AppointmentInterestItem) {
        for (i in 0 until interestArrayList.size) {
            if (interestArrayList[i]._id == appointmentInterestItem._id) {
                interestArrayList[i] = appointmentInterestItem
                notifyItemChanged(i)
                break
            }
        }
    }

    class ViewHolder(val binding: ItemScheduledAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val moreOptions: ImageView = itemView.findViewById(R.id.ivMore)
    }


    interface onScheduleAppointment {
        fun onViewRescheduleAppointment(interest: AppointmentInterestItem, position: Int)
        fun onViewDeleteAppointment(interest: AppointmentInterestItem, position: Int)
    }
}