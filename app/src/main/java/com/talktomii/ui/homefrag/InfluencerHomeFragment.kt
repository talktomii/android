package com.talktomii.ui.homefrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.data.model.getallslotbydate.PayloadAppoinment
import com.talktomii.databinding.FragmentHomeInfluencerBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.ui.callhistory.models.CallHistoryPayoad
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import com.talktomii.utlis.listner.CallHistory
import com.talktomii.utlis.listner.InfluenceListener
import com.talktomii.viewmodel.InfluenceHomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfluencerHomeFragment : DaggerFragment(), CommonInterface, InfluenceListener, CallHistory {

    private lateinit var binding: FragmentHomeInfluencerBinding
    private var nearestAppointment: AdapterNearestAppointment? = null
    private var myAudienceAdapter: AdapterMyAudience? = null

    @Inject
    lateinit var viewModel: InfluenceHomeViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    private lateinit var progressDialog: ProgressDialog


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
        myAudienceAdapter = AdapterMyAudience(requireContext())
        binding.rvMyAudience.adapter = myAudienceAdapter

        setListener()
        init()
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.infulancerListner = this
        viewModel.callHistoryListener = this
        viewModel.getCurrentWallet(getUser(prefsManager)!!.admin._id)

        viewModel.getAllAppoinemnt(getUser(prefsManager)!!.admin._id)
        viewModel.getUsersCallHistory(getUser(prefsManager)!!.admin._id)
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()

    }

    override fun onFailureAPI(message: String) {
        progressDialog.dismiss()

    }

    override fun onStarted() {
        progressDialog.show()

    }

    override fun influenceList(payload: PayloadAppoinment) {
        progressDialog.dismiss()
        if (payload.interest?.size ?: 0 > 0) {
            nearestAppointment!!.setList(payload.interest)
            binding.txtNearestAppoint.visibility = View.VISIBLE
        } else {
            binding.txtNearestAppoint.visibility = View.GONE
        }
    }

    override fun getCallHistory(payload: CallHistoryPayoad?) {
        progressDialog.dismiss()
        if (payload?.callHistory?.size ?: 0 > 0) {
            myAudienceAdapter!!.setList(payload!!.callHistory)
            binding.txtMyAudience.visibility = View.VISIBLE
        } else {
            binding.txtMyAudience.visibility = View.GONE
        }
    }
}