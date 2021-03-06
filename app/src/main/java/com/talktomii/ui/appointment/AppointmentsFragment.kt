package com.talktomii.ui.appointment

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.talktomii.R
import com.talktomii.data.model.TimeSlotSpinner
import com.talktomii.data.model.appointment.AppointmentByIdPayload
import com.talktomii.data.model.appointment.AppointmentInterestItem
import com.talktomii.data.model.appointment.AppointmentPayload
import com.talktomii.data.model.appointment.UpdateAppointmentPayload
import com.talktomii.data.model.getallslotbydate.Payload
import com.talktomii.data.model.getallslotbydate.TimeSlotsWithData
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.CallDialogBinding
import com.talktomii.databinding.FragmentAppointmentsBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.DeleteAppointmentListener
import com.talktomii.interfaces.OnSlotSelectedInterface
import com.talktomii.interfaces.RescheduleAppointmentListener
import com.talktomii.ui.home.AdapterHomeTimeSlot
import com.talktomii.utlis.DateUtils
import com.talktomii.utlis.DateUtils.convertStringToCalender
import com.talktomii.utlis.DateUtils.convertStringToCalenderWithOne
import com.talktomii.utlis.DateUtils.getFormatedFullDate
import com.talktomii.utlis.DateUtils.simpleDateToUTCTOLocalDate
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.common.CommonUtils.Companion.showToastMessage
import com.talktomii.utlis.common.Constants
import com.talktomii.utlis.common.Constants.Companion.DATE
import com.talktomii.utlis.common.Constants.Companion.STATUS
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import com.talktomii.utlis.listner.AddInfluncerItem
import com.talktomii.utlis.listner.InfluenceCalenderListener
import com.talktomii.utlis.listner.InfluncerItem
import com.talktomii.utlis.view.EventDecorator
import com.talktomii.viewmodel.InfluenceHomeViewModel
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import okhttp3.ResponseBody
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AppointmentsFragment : DaggerFragment(), OnSlotSelectedInterface, CommonInterface,
    InfluenceCalenderListener, AdapterScheduledAppointment.onScheduleAppointment,
    DeleteAppointmentListener, RescheduleAppointmentListener, InfluncerItem, AddInfluncerItem {

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
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false)
//        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_YES -> {
//                binding.calendarViewAppointment.setDateTextAppearance(R.color.white)
//                binding.calendarViewAppointment.swi
//            }
//            Configuration.UI_MODE_NIGHT_NO -> {
//                binding.calendarViewAppointment.setDateTextAppearance(R.color.black)
//            }
//        }
        return binding.root
    }

    private fun init() {
        viewModel.commonInterface = this
        viewModel.infulancerCalenderListner = this
        viewModel.onSlotSelectedInterface = this
        viewModel.deleteAppointmentListener = this
        viewModel.rescheduleAppointmentListener = this
        viewModel.influncerItem = this
        viewModel.addInfluncerItem = this
        progressDialog = ProgressDialog(requireActivity())
        initAdapter()
        viewModel.getAllAppointmentByCalender(getUser(prefsManager)!!.admin._id)
        binding.calendarViewAppointment.setOnDateChangedListener { widget, date, selected ->
            val selectedDate = "" + date.year + "-" + date.month + "-" + date.day
            viewModel.getAllAppointmentByDate(selectedDate, getUser(prefsManager)!!.admin._id)
        }
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
        progressDialog.dismiss()
        availableTimeSlots = timeStop
        setTimeSlot()
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {
        progressDialog.dismiss()
        showToastMessage(requireContext(), message)
        ApisRespHandler.handleError(
            ApiUtils.handleError(
                code,
                errorBody!!.string()
            ), requireActivity(), prefsManager
        )
    }

    override fun onStarted() {
        progressDialog.show()
    }

    override fun influenceCalenderList(payload: AppointmentPayload) {
        progressDialog.dismiss()
        payload.Appointment!!.sortByDescending {
            SimpleDateFormat("yyyy-MM-dd").format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(it.date)
            )
        }


        val calenderArrayList: ArrayList<CalendarDay> = arrayListOf()
        for (i in payload.Appointment!!) {
            val calender = convertStringToCalenderWithOne(i.date)
            calenderArrayList.add(
                CalendarDay.from(
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)
                )
            )
        }
        binding.calendarViewAppointment.addDecorator(
            EventDecorator(requireContext().resources.getColor(R.color.blue), calenderArrayList)
        )
        if (payload.Appointment?.size ?: 0 > 0) {
            binding.rlNoDataFound.visibility = View.GONE
            binding.rvScheduled.visibility = View.VISIBLE
            binding.tvLabeScheduleAppointment.visibility = View.VISIBLE
            binding.imageView3.visibility = View.VISIBLE
            appointmentAdapter!!.setList(payload.Appointment)
        } else {
            binding.rlNoDataFound.visibility = View.VISIBLE
            binding.rvScheduled.visibility = View.GONE
            binding.tvLabeScheduleAppointment.visibility = View.GONE
            binding.imageView3.visibility = View.GONE
        }
//        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_YES -> {
//
//                binding.calendarViewAppointment.addDecorator(
//                    EventDecorator(
//                        R.color.siq_divider_color_light,
//                        calenderArrayList
//                    )
//                )
//                binding.calendarViewAppointment.setDateTextAppearance(R.color.white)
//                binding.calendarViewAppointment.setWeekDayTextAppearance(R.color.white)
//                binding.calendarViewAppointment.setWeekDayTextAppearance(R.color.white)
//                binding.calendarViewAppointment.invalidate()
//            }
//            Configuration.UI_MODE_NIGHT_NO -> {
//                binding.calendarViewAppointment.addDecorator(
//                    EventDecorator(
//                        R.color.siq_color_primary_dark,
//                        calenderArrayList
//                    )
//                )
//                binding.calendarViewAppointment.setDateTextAppearance(R.color.black)
//                binding.calendarViewAppointment.setWeekDayTextAppearance(R.color.black)
//                binding.calendarViewAppointment.setWeekDayTextAppearance(R.color.black)
//                binding.calendarViewAppointment.invalidate()
//            }
//        }

    }

    var reScheduleAppointmentDialog: BottomSheetDialog? = null
    var deleteAppointmentDialog: BottomSheetDialog? = null
    override fun onViewRescheduleAppointment(interest: AppointmentInterestItem, position: Int) {
        progressDialog.dismiss()
        selectedItemForReschedule = interest
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {

                reScheduleAppointmentDialog = BottomSheetDialog(
                    requireContext(),
                    R.style.MyTransparentBottomSheetDialogThemeDark
                )
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.bottomsheet_reschedule_appointment, null)
                val btnClose = view.findViewById<ImageView>(R.id.btnCloseAppointmentSheet)

                recycleViewRescheduleSlot =
                    view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)
                spinnerTimeDuration = view.findViewById<Spinner>(R.id.spinnerTimeDuration)
                val tvRescheduleAppointment =
                    view.findViewById<TextView>(R.id.tvRescheduleAppointment)
//        val rvTimeSlotAppointment = view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)

                btnClose.setBackgroundResource(R.drawable.closesheeticon_dark)
                val date_view = view.findViewById<HorizontalCalendarView>(R.id.calendarViewa)
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
                recycleViewRescheduleSlot!!.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                tvRescheduleAppointment.setOnClickListener {
                    val hashMap: java.util.HashMap<String, Any> = hashMapOf()
//            hashMap[Constants.IF_ID] = viewModel.userField.get()!!._id
//            hashMap[Constants.UID] = getUser(prefsManager)!!.admin._id
                    hashMap[DATE] = selectedDate!!
                    hashMap[Constants.START_TIME] = selectedStartTime!!
                    hashMap[Constants.END_TIME] = selectedEndTime!!
                    hashMap[Constants.STATUS] = "Rescheduled"
                    hashMap[Constants.DURATON] = selectedTimeSlots!!.time

                    viewModel.updateAppointment(selectedItemForReschedule!!._id, hashMap)
                }
                btnClose.setOnClickListener {
                    progressDialog.dismiss()
                    reScheduleAppointmentDialog!!.dismiss()
                }
                getTimeSlots(Calendar.getInstance())
                reScheduleAppointmentDialog!!.setCancelable(false)
                reScheduleAppointmentDialog!!.setContentView(view)
                reScheduleAppointmentDialog!!.show()
            }
            Configuration.UI_MODE_NIGHT_NO -> {

                reScheduleAppointmentDialog = BottomSheetDialog(
                    requireContext(),
                    R.style.MyTransparentBottomSheetDialogTheme
                )
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.bottomsheet_reschedule_appointment, null)
                val btnClose = view.findViewById<ImageView>(R.id.btnCloseAppointmentSheet)

                recycleViewRescheduleSlot =
                    view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)
                spinnerTimeDuration = view.findViewById<Spinner>(R.id.spinnerTimeDuration)
                val tvRescheduleAppointment =
                    view.findViewById<TextView>(R.id.tvRescheduleAppointment)
//        val rvTimeSlotAppointment = view.findViewById<RecyclerView>(R.id.rvTimeSlotAppointment)
                btnClose.setBackgroundResource(R.drawable.close_sheet_icon)
                val date_view = view.findViewById<HorizontalCalendarView>(R.id.calendarViewa)
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
                        progressDialog.dismiss()
                        getTimeSlots(date)
                    }
                }
                recycleViewRescheduleSlot!!.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                tvRescheduleAppointment.setOnClickListener {
                    val hashMap: java.util.HashMap<String, Any> = hashMapOf()
//            hashMap[Constants.IF_ID] = viewModel.userField.get()!!._id
//            hashMap[Constants.UID] = getUser(prefsManager)!!.admin._id
                    hashMap[DATE] = selectedDate!!
                    hashMap[Constants.START_TIME] = selectedStartTime!!
                    hashMap[Constants.END_TIME] = selectedEndTime!!
                    hashMap[Constants.STATUS] = "Rescheduled"
                    hashMap[Constants.DURATON] = selectedTimeSlots!!.time

                    viewModel.updateAppointment(selectedItemForReschedule!!._id, hashMap)
                }
                btnClose.setOnClickListener {
                    progressDialog.dismiss()
                    reScheduleAppointmentDialog!!.dismiss()
                }
                getTimeSlots(Calendar.getInstance())
                reScheduleAppointmentDialog!!.setCancelable(false)
                reScheduleAppointmentDialog!!.setContentView(view)
                reScheduleAppointmentDialog!!.show()
            }
        }

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
        progressDialog.dismiss()
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
            val hashMap = HashMap<String, Any>()
            hashMap[STATUS] = "Cancelled"
            viewModel.deleteAppointment(selectedItemForReschedule!!._id, hashMap, position)
            dialog.dismiss()
        }
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCallButtonClick(interest: AppointmentInterestItem, position: Int) {
        //Check Call Regarding Conditions
        val currentTime = getFormatedFullDate(Calendar.getInstance())
        val calenderStartTime = simpleDateToUTCTOLocalDate(interest.startTime)
        val calenderEnd = convertStringToCalender(calenderStartTime)
        calenderEnd[Calendar.MINUTE] = calenderEnd[Calendar.MINUTE] - 1
        val calenderEndTime = getFormatedFullDate(calenderEnd)

        val isBetween = DateUtils.checkTimeIsBetween(calenderEndTime, calenderStartTime, currentTime)
        if (isBetween) {
            showPopup(interest)
        } else {
            context?.let { showToastMessage(it, getString(R.string.call_button_availble)) }
        }
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
            R.layout.drop_down_custom_layout, arrayList
        )
        adapter.setDropDownViewResource(R.layout.spinner_list)
        spinnerTimeDuration!!.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedTimeSlots = availableTimeSlots!!.timeStops[position]
                    (view as TextView).setTextColor(resources.getColor(R.color.calText))
                    val arrayList: ArrayList<TimeSlotsWithData> = arrayListOf()
                    for (i in availableTimeSlots!!.timeStops[position].slot) {
                        arrayList.add(TimeSlotsWithData(i, false))
                    }
                    recycleViewRescheduleSlot!!.adapter =
                        AdapterHomeTimeSlot(requireContext(), arrayList,
                            object : AdapterHomeTimeSlot.onViewItemClick {
                                override fun onViewItemTimeSelect(text: String, s: String) {
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
        progressDialog.dismiss()
        viewModel.getAppointmentById(admin.Item._id)
    }

    override fun onRescheduleAppointment(admin: UpdateAppointmentPayload) {
        progressDialog.dismiss()
        viewModel.getAppointmentById(admin.Item._id)
        reScheduleAppointmentDialog!!.dismiss()
    }

    override fun influenceItem(payload: AppointmentPayload) {
        progressDialog.dismiss()
        if (payload.Appointment?.size ?: 0 > 0) {
            appointmentAdapter?.addItemsList(payload.Appointment)
            binding.rlNoDataFound.visibility = View.GONE
            binding.rvScheduled.visibility = View.VISIBLE
            binding.imageView3.visibility = View.VISIBLE
            binding.tvLabeScheduleAppointment.visibility = View.VISIBLE
        } else {
            appointmentAdapter?.clearList()
            binding.rlNoDataFound.visibility = View.VISIBLE
            binding.rvScheduled.visibility = View.GONE
            binding.tvLabeScheduleAppointment.visibility = View.GONE
            binding.imageView3.visibility = View.GONE
        }

    }

    private fun showPopup(interest: AppointmentInterestItem) {
        var customDialog: AlertDialog? = null
        val customDialogBuilder =
            AlertDialog.Builder(requireContext())
        val customView = CallDialogBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )
        customView.txtCall.setOnClickListener {
            customDialog?.cancel()
            view?.findNavController()
                ?.navigate(R.id.callFragment, bundleOf("DATA" to Gson().toJson(interest)))

//            userData=Admin1(_id = "625e09d929499b944fc9e6a5")
//            view?.findNavController()
//                ?.navigate(R.id.callFragment, bundleOf("DATA" to Gson().toJson(userData)))
        }
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                customView.ivCancel.setImageResource(R.drawable.closesheeticon_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                customView.ivCancel.setImageResource(R.drawable.close_sheet_icon)
            }
        }
        customView.ivCancel.setOnClickListener {
            customDialog?.dismiss()
        }
        customDialogBuilder.setView(customView.root)
        customDialog = customDialogBuilder.create()
        customDialog?.setCancelable(true)
        customDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
    }

    override fun addInfluenceItem(payload: AppointmentByIdPayload) {
        progressDialog.dismiss()
        if (payload.Appointment != null) {
            appointmentAdapter?.addItem(payload.Appointment)

        }
    }

}