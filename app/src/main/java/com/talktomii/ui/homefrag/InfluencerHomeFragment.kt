package com.talktomii.ui.homefrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.databinding.FragmentHomeInfluencerBinding
import com.talktomii.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfluencerHomeFragment : DaggerFragment() {

    private lateinit var binding: FragmentHomeInfluencerBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeInfluencerBinding.inflate(inflater, container, false)

        return binding.root

    }

    private fun setListener() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNearestAppointment.adapter = AdapterNearestAppointment()
        binding.rvMyAudience.adapter = AdapterMyAudience()

        setListener()
    }
}