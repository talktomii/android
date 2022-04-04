package com.talktomii.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.talktomii.databinding.FragmentInfluencerProfileBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.utlis.CallDialog
import com.talktomii.utlis.DeleteAppointmentDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener


import devs.mulham.horizontalcalendar.HorizontalCalendar
import java.util.*


class InfluencerProfileFragment : DaggerFragment() {

    private lateinit var binding: FragmentInfluencerProfileBinding


    @Inject
    lateinit var viewModel: HomeViewModel

    private var horizontalCalendar: HorizontalCalendar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInfluencerProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    private fun setListener() {
        binding.txtBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.txtCallNow.setOnClickListener {
            val dialog = CallDialog()
            dialog.show(requireActivity().supportFragmentManager, CallDialog.TAG)
        }

//        binding.txtBookACall.setOnClickListener {
//            view?.findNavController()?.navigate(com.talktomii.R.id.action_influencer_profile_to_call_fragmnet)
//        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvInterest.adapter = AdapterInterests()

        setListener()


        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)
        val startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)


        horizontalCalendar =
            HorizontalCalendar.Builder(requireActivity(), com.talktomii.R.id.calendarView)
                .range(startDate, endDate)
                .configure()
                .showTopText(false)
                .end()
                .datesNumberOnScreen(5)
                .build()
        horizontalCalendar!!.calendarListener = object : HorizontalCalendarListener() {

            override fun onDateSelected(date: Calendar?, position: Int) {


            }
        }


        binding.rvTimeSlot.adapter = AdapterTimeSlot()


    }
}