package com.talktomii.ui.appointment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.appointment.AppointmentInterestItem
import com.talktomii.databinding.ItemScheduledAppointmentBinding
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.utlis.DateUtils.setDateToTime
import com.talktomii.utlis.DateUtils.setDateToWeekDate
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import java.util.*
import javax.inject.Inject


class AdapterScheduledAppointment(var webService: WebService? = null) :
    RecyclerView.Adapter<AdapterScheduledAppointment.ViewHolder>() {
    private var context: Context? = null

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
        holder.binding.txtTime.text = setDateToTime(interest.startTime) + "-" + setDateToTime(interest.endTime)
        holder.binding.tvDayAndDate.text =
            setDateToWeekDate(interest.date)

        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_adelete -> {
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.mybank_delete_popup)
                        dialog.window!!.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        val close = dialog.findViewById(R.id.CancelBankBtn) as TextView
                        val delete = dialog.findViewById(R.id.deleteBankBtn) as TextView
                        val text = dialog.findViewById(R.id.deleteBankDetailText) as TextView
                        text.text = context!!.getString(R.string.delete_appointment)
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        delete.setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                    R.id.action_rescedule -> {

                        val dialog = BottomSheetDialog(
                            context!!,
                            R.style.MyTransparentBottomSheetDialogTheme
                        )
                        val view = LayoutInflater.from(context)
                            .inflate(R.layout.bottomsheet_reschedule_appointment, null)
                        val btnClose = view.findViewById<ImageView>(R.id.btnCloseAppointmentSheet)
                        val recycleView =
                            view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)
                        val date_view =
                            view.findViewById<HorizontalCalendarView>(R.id.calendarViewa)
                        val startDate: Calendar = Calendar.getInstance()
                        val endDate: Calendar = Calendar.getInstance()
                        endDate.add(Calendar.DAY_OF_MONTH, +7)
                        AppointmentsFragment.horizontalCalendar = HorizontalCalendar.Builder(
                            view, date_view.id
                        )
                            .range(startDate, endDate)
                            .configure()
                            .showTopText(false)
                            .end()
                            .build()
//                        AppointmentsFragment.horizontalCalendar!!.calendarListener = object : HorizontalCalendarListener() {
//                            override fun onDateSelected(date: Calendar?, position: Int) {
//                                if (!::viewModel.isInitialized) {
//                                    viewModel = HomeScreenViewModel(webService!!)
//                                }
//                                viewModel.getAllSlotByDate(SimpleDateFormat("yyyy-MM-dd").format(date!!.time))
//                            }
//                        }
                        recycleView.layoutManager = LinearLayoutManager(
                            context,
                            LinearLayoutManager.HORIZONTAL,
                            true
                        )
                        val adapter = AdapterRescheduleTimeSlot()
                        recycleView.adapter = adapter

                        btnClose.setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.setCancelable(false)
                        dialog.setContentView(view)
                        dialog.show()
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

    class ViewHolder(val binding: ItemScheduledAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val moreOptions: ImageView = itemView.findViewById(R.id.ivMore)
    }

}