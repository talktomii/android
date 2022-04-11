package com.talktomii.ui.appointment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.ui.callhistory.adapters.CallHistoryAdapter
import com.talktomii.ui.home.HomeScreenViewModel
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import java.util.*
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager


class AdapterScheduledAppointment(var webService: WebService? = null) : RecyclerView.Adapter<AdapterScheduledAppointment.ViewHolder>() {
    private var context: Context? = null
    @Inject
    lateinit var viewModel: HomeScreenViewModel
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterScheduledAppointment.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scheduled_appointment, parent, false)
        context = parent.context
        webService = this.webService
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_adelete -> {
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.mybank_delete_popup)
                        dialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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

                        val dialog = BottomSheetDialog(context!!, R.style.MyTransparentBottomSheetDialogTheme)
                        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_reschedule_appointment, null)
                        val btnClose = view.findViewById<ImageView>(R.id.btnCloseAppointmentSheet)
                        val recycleView = view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)
                        val date_view = view.findViewById<HorizontalCalendarView>(R.id.calendarViewa)
                        val startDate: Calendar = Calendar.getInstance()
                        val endDate: Calendar = Calendar.getInstance()
                        endDate.add(Calendar.DAY_OF_MONTH, +7)
                        AppointmentsFragment.horizontalCalendar = HorizontalCalendar.Builder(
                            view, date_view.id)
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

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val moreOptions: ImageView = itemView.findViewById(R.id.ivMore)
    }

}