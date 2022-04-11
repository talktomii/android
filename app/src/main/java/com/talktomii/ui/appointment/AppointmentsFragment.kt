package com.talktomii.ui.appointment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talktomii.R
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.databinding.FragmentAppointmentsBinding
import com.talktomii.interfaces.OnSlotSelectedInterface
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.home.profile.AdapterTimeSlot
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AppointmentsFragment : DaggerFragment() , OnSlotSelectedInterface {

    private lateinit var binding: FragmentAppointmentsBinding

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false)
        context?.let { Companion.getContext(it) }
        return binding.root

        setListener()

    }

    private fun setListener() {


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvScheduled.adapter = AdapterScheduledAppointment()
        acct = requireActivity()

    }

    companion object {
        lateinit var context : Context
        fun getContext(context: Context){
            this.context = context
        }
        lateinit var  recycleView : RecyclerView
        var horizontalCalendar: HorizontalCalendar? = null
        lateinit var acct : Activity
    }
    override fun onslotselect(timeStop: TimeStop) {
        recycleView.adapter = AdapterTimeSlot(timeStop)
    }
}