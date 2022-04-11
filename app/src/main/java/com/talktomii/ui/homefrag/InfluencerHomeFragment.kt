package com.talktomii.ui.homefrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.data.model.getallslotbydate.PayloadAppoinment
import com.talktomii.databinding.FragmentHomeInfluencerBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.getUser
import com.talktomii.utlis.listner.InfulancerListner
import com.talktomii.utlis.uid
import com.talktomii.viewmodel.InfluenceHomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfluencerHomeFragment : DaggerFragment(), CommonInterface , InfulancerListner {

    private lateinit var binding: FragmentHomeInfluencerBinding
    private var nearestAppointment : AdapterNearestAppointment? = null

    @Inject
    lateinit var viewModel: InfluenceHomeViewModel
    @Inject
    lateinit var prefsManager: PrefsManager


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
        nearestAppointment = AdapterNearestAppointment()
        binding.rvNearestAppointment.adapter = nearestAppointment
        binding.rvMyAudience.adapter = AdapterMyAudience()

        setListener()
        init()
    }

    private fun init() {
        viewModel.commonInterface = this
        viewModel.infulancerListner = this
        viewModel.getCurrentWallet(getUser(prefsManager)!!.admin._id)
        viewModel.getAllAppoinemnt(getUser(prefsManager)!!.admin._id)
    }

    override fun onFailure(message: String) {

    }

    override fun onFailureAPI(message: String) {
    }

    override fun onStarted() {

    }

    override fun infulancerList(payload: PayloadAppoinment) {
        nearestAppointment!!.setList(payload.interest)
    }
}