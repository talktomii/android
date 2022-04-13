package com.talktomii.ui.appointment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talktomii.R
import com.talktomii.data.model.TimeSlotSpinner
import com.talktomii.data.model.appointment.AppointmentInterestItem
import com.talktomii.data.model.appointment.AppointmentPayload
import com.talktomii.data.model.appointment.UpdateAppointmentPayload
import com.talktomii.data.model.getallslotbydate.Payload
import com.talktomii.data.model.getallslotbydate.TimeSlotsWithData
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.databinding.FragmentAppointmentsBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.DeleteAppointmentListener
import com.talktomii.interfaces.OnSlotSelectedInterface
import com.talktomii.ui.home.AdapterHomeTimeSlot
import com.talktomii.utlis.DateUtils
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.getUser
import com.talktomii.utlis.listner.InfluenceCalenderListener
import com.talktomii.viewmodel.InfluenceHomeViewModel
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AppointmentsFragment : DaggerFragment(), OnSlotSelectedInterface, CommonInterface,
    InfluenceCalenderListener, AdapterScheduledAppointment.onScheduleAppointment,
    DeleteAppointmentListener {

    private lateinit var binding: FragmentAppointmentsBinding

    @Inject
    lateinit var viewModel: InfluenceHomeViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    var appointmentAdapter: AdapterScheduledAppointment? = null
    private var horizontalCalendar: HorizontalCalendar? = null
    private var selectedDate: String? = null
    private var selectedTimeSlots: TimeStop? = null
    private var availableTimeSlots: Payload? = null
    private var selectedStartTime: String? = null
    private var selectedEndTime: String? = null
    var recycleViewRescheduleSlot: RecyclerView? = null
    var spinnerTimeDuration: Spinner? = null
    private var selectedItemForReschedule: AppointmentInterestItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun init() {
        viewModel.commonInterface = this
        viewModel.infulancerCalenderListner = this
        viewModel.onSlotSelectedInterface = this
        viewModel.deleteAppointmentListener = this

        initAdapter()
        viewModel.getAllAppointmentByCalender(getUser(prefsManager)!!.admin._id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun initAdapter() {
        appointmentAdapter = AdapterScheduledAppointment(requireContext(), this)
        binding.rvScheduled.adapter = appointmentAdapter
    }


    override fun onSlotTimesList(timeStop: Payload) {

        availableTimeSlots = timeStop
        setTimeSlot()
    }

    override fun onFailure(message: String) {

    }

    override fun onFailureAPI(message: String) {
    }

    override fun onStarted() {
    }

    override fun influenceCalenderList(payload: AppointmentPayload) {
        appointmentAdapter!!.setList(payload.interest)
    }

    override fun onViewRescheduleAppointment(interest: AppointmentInterestItem, position: Int) {
        selectedItemForReschedule = interest
        val dialog = BottomSheetDialog(
            requireContext(),
            R.style.MyTransparentBottomSheetDialogTheme
        )
        val view = LayoutInflater.from(context)
            .inflate(R.layout.bottomsheet_reschedule_appointment, null)
        val btnClose = view.findViewById<ImageView>(R.id.btnCloseAppointmentSheet)
        recycleViewRescheduleSlot =
            view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)
        spinnerTimeDuration =
            view.findViewById<Spinner>(R.id.spinnerTimeDuration)

        var tvRescheduleAppointment =
            view.findViewById<TextView>(R.id.tvRescheduleAppointment)


        var rvTimeSlotAppointment =
            view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)


        val date_view =
            view.findViewById<HorizontalCalendarView>(R.id.calendarViewa)
        val startDate: Calendar = Calendar.getInstance()
        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.DAY_OF_MONTH, +7)

        horizontalCalendar =
            HorizontalCalendar.Builder(view, date_view.id)
                .range(startDate, endDate)
                .configure()
                .showTopText(false)
                .end()
                .datesNumberOnScreen(7)
                .build()

        horizontalCalendar!!.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                getTimeSlots(date)
            }
        }
        rvTimeSlotAppointment.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        tvRescheduleAppointment.setOnClickListener {
//            viewModel.updateAppointment(selectedItemForReschedule)
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        getTimeSlots(Calendar.getInstance())
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun getTimeSlots(date: Calendar?) {
        selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date!!.time)
        if (selectedItemForReschedule != null || selectedItemForReschedule!!.ifid != null || selectedItemForReschedule!!.ifid._id != null)
            viewModel.getAllSlotByDate(
                selectedDate.toString(),
                selectedItemForReschedule!!.ifid._id
            )
    }

    override fun onViewDeleteAppointment(interest: AppointmentInterestItem, position: Int) {
        selectedItemForReschedule = interest

        val dialog = Dialog(requireContext())
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
        text.text = requireContext().getString(R.string.delete_appointment)
        close.setOnClickListener {
            dialog.dismiss()
        }
        delete.setOnClickListener {
            viewModel.deleteAppointment(selectedItemForReschedule!!._id, true, position)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setTimeSlot() {

        val arrayList: ArrayList<TimeSlotSpinner> = arrayListOf()
        for (i in 0 until (availableTimeSlots?.timeStops?.size ?: 0)) {
            arrayList.add(
                TimeSlotSpinner(
                    availableTimeSlots!!.timeStops[i].time, i
                )
            )
        }

        val adapter: ArrayAdapter<TimeSlotSpinner> = ArrayAdapter<TimeSlotSpinner>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeDuration!!.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedTimeSlots = availableTimeSlots!!.timeStops[position]
                    var arrayList: ArrayList<TimeSlotsWithData> = arrayListOf()
                    for (i in availableTimeSlots!!.timeStops[position].slot) {
                        arrayList.add(TimeSlotsWithData(i, false))

                    }
                    recycleViewRescheduleSlot!!.adapter =
                        AdapterHomeTimeSlot(requireContext(), arrayList,
                            object : AdapterHomeTimeSlot.onViewItemClick {
                                override fun onViewItemTimeSelect(text: String) {
                                    selectedStartTime = text
                                    try {
                                        selectedEndTime = DateUtils.addMinutes(
                                            selectedStartTime!!,
                                            selectedTimeSlots!!.time
                                        )
                                    } catch (e: Exception) {

                                    }

                                }

                            })

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinnerTimeDuration!!.adapter = adapter
    }

    override fun onDeleteAppointment(admin: UpdateAppointmentPayload, position: Int) {
        appointmentAdapter?.removeItemList(position)
    }

}