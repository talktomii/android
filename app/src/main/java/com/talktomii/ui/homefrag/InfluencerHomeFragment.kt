package com.talktomii.ui.homefrag

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.talktomii.R
import com.talktomii.data.model.admin1.PayloadDashBoard
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.FragmentHomeInfluencerBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.InfluencerDashboardInterface
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import com.talktomii.viewmodel.InfluenceHomeViewModel
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import javax.inject.Inject

class InfluencerHomeFragment : DaggerFragment(), CommonInterface, InfluencerDashboardInterface {

    private lateinit var binding: FragmentHomeInfluencerBinding
    private var nearestAppointment: AdapterNearestAppointment? = null
    private var myAudienceAdapter: AdapterMyAudience? = null
    private var provinceList: ArrayList<String> = arrayListOf()

    @Inject
    lateinit var viewModel: InfluenceHomeViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeInfluencerBinding.inflate(inflater, container, false)
        val role: SharedPreferences = requireContext().getSharedPreferences("RoleName",
            Context.MODE_PRIVATE
        )
        val roleName = role.getString("name","").toString()
        if(roleName == "user"){
            MainActivity.bookMark.visibility = View.VISIBLE
        }else{
            MainActivity.bookMark.visibility = View.GONE
        }
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivCross.setBackgroundResource(R.drawable.ic_cross_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivCross.setBackgroundResource(R.drawable.ic_cross_light)
            }
        }
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
        provinceList.add("Weekly")
        provinceList.add("Monthly")
        provinceList.add("Yearly")
        val admin = getUser(prefsManager)?.admin
        if (admin!!.fname != null) {
            binding.txtName.text =  admin.fname
        } else {
            binding.txtName.text = admin.name
        }
        binding.spinnerWeek.item = provinceList as List<Any>?

        binding.spinnerWeek.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position.equals(0)) {
                    viewModel.getInfluencerDashboard("weekly", getUser(prefsManager)!!.admin._id)
                } else if (position.equals(1)) {
                    viewModel.getInfluencerDashboard("monthly", getUser(prefsManager)!!.admin._id)
                } else if (position.equals(2)) {
                    viewModel.getInfluencerDashboard("yearly", getUser(prefsManager)!!.admin._id)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        viewModel.commonInterface = this
//        viewModel.infulancerListner = this
//        viewModel.callHistoryListener = this
        viewModel.influencerDashboardInterface = this
//        viewModel.getCurrentWallet(getUser(prefsManager)!!.admin._id)
//        viewModel.getAllAppoinemnt(getUser(prefsManager)!!.admin._id)
//        viewModel.getUsersCallHistory(getUser(prefsManager)!!.admin._id)
        viewModel.getInfluencerDashboard("weekly", getUser(prefsManager)!!.admin._id)
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {
        progressDialog.dismiss()
        ApisRespHandler.handleError(
            ApiUtils.handleError(code, errorBody!!.string()),
            requireActivity(),
            prefsManager
        )
    }

    override fun onStarted() {
        progressDialog.show()
    }

//    override fun influenceList(payload: PayloadAppoinment) {
//        progressDialog.dismiss()
//        if (payload.interest?.size ?: 0 > 0) {
//            nearestAppointment!!.setList(payload.interest)
//            binding.txtNearestAppoint.visibility = View.VISIBLE
//        } else {
//            binding.txtNearestAppoint.visibility = View.GONE
//        }
//    }

    override fun onData(payload: PayloadDashBoard) {
        progressDialog.dismiss()
        binding.txtEarn.text = "$${payload.earning}"
        binding.txtCallsEarn.text = payload.totalHours
        if (payload.nearestAppointment?.size ?: 0 > 0) {
            nearestAppointment!!.setList(payload.nearestAppointment)
            binding.txtNearestAppoint.visibility = View.VISIBLE
        } else {
            binding.txtNearestAppoint.visibility = View.GONE
        }

        if (payload.usersData?.size ?: 0 > 0) {
            myAudienceAdapter!!.setList(payload.usersData!!)
            binding.txtMyAudience.visibility = View.VISIBLE
        } else {
            binding.txtMyAudience.visibility = View.GONE
        }

    }

}