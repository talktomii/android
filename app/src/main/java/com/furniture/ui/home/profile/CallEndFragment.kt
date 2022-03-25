package com.furniture.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.databinding.CallEndFragmentBinding
import com.furniture.databinding.FragmentCallBinding
import com.furniture.ui.home.HomeViewModel
import com.furniture.utlis.DeleteAppointmentDialog
import com.furniture.utlis.ExtendTimeDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CallEndFragment : DaggerFragment() {

    private lateinit var binding: CallEndFragmentBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = CallEndFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }
    private fun setListener() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLeaveTips.adapter = AdapterLeaveTip()
        binding.rvMyBudges.adapter = AdapterMyBudges()
        setListener()

    }
}
