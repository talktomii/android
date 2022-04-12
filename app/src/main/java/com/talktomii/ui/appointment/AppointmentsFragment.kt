package com.talktomii.ui.appointment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.appointment.AppointmentPayload
import com.talktomii.data.model.getallslotbydate.Payload
import com.talktomii.databinding.FragmentAppointmentsBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.OnSlotSelectedInterface
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.getUser
import com.talktomii.utlis.listner.InfulancerCalenderListner
import com.talktomii.viewmodel.InfluenceHomeViewModel
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import javax.inject.Inject

class AppointmentsFragment : DaggerFragment(), OnSlotSelectedInterface, CommonInterface,
    InfulancerCalenderListner {

    private lateinit var binding: FragmentAppointmentsBinding

    @Inject
    lateinit var viewModel: InfluenceHomeViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    var appointmentAdapter: AdapterScheduledAppointment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false)
        context?.let { Companion.getContext(it) }
        return binding.root
    }


    private fun init() {
        viewModel.commonInterface = this
        viewModel.infulancerCalenderListner = this

        initAdapter()
        viewModel.getAllAppointmentByCalender(getUser(prefsManager)!!.admin._id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        acct = requireActivity()

    }

    private fun initAdapter() {
        appointmentAdapter = AdapterScheduledAppointment()
        binding.rvScheduled.adapter = appointmentAdapter
    }

    companion object {
        lateinit var context: Context
        fun getContext(context: Context) {
            this.context = context
        }

        lateinit var recycleView: RecyclerView
        var horizontalCalendar: HorizontalCalendar? = null
        lateinit var acct: Activity
    }

    override fun onslotselect(timeStop: Payload) {
//        recycleView.adapter = AdapterTimeSlot(timeStop)
    }

    override fun onFailure(message: String) {

    }

    override fun onFailureAPI(message: String) {
    }

    override fun onStarted() {
    }

    override fun infulancerCalenderList(payload: AppointmentPayload) {
        appointmentAdapter!!.setList(payload.interest)
    }
}