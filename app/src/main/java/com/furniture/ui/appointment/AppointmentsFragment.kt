package com.furniture.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import com.furniture.databinding.FragmentAppointmentsBinding
import com.furniture.ui.home.HomeViewModel
import com.furniture.utlis.CallDialog
import com.furniture.utlis.DeleteAppointmentDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AppointmentsFragment: DaggerFragment() {

    private lateinit var binding: FragmentAppointmentsBinding


    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false)
        return binding.root

        setListener()

    }

    private fun setListener() {


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvScheduled.adapter = AdapterScheduledAppointment()



    }
}