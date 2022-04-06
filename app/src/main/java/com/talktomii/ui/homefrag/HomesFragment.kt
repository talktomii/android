package com.talktomii.ui.homefrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.data.model.admin.Admin
import com.talktomii.data.model.admin.Payload
import com.talktomii.databinding.HomeFragmentBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.HomeInterface
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomesFragment : DaggerFragment(R.layout.home_fragment), HomeInterface, CommonInterface,
    AdapterPopular.onViewPopularClick {

    private lateinit var binding: HomeFragmentBinding
    private val viewModels by viewModels<HomeScreenViewModel>()
    private var adapterPopular: AdapterPopular? = null
    private var popularArrayList: ArrayList<Admin> = arrayListOf()

    @Inject
    lateinit var viewModel: HomeScreenViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.vm = viewModels
        initAdapter()
        init()
    }

    private fun initAdapter() {
        adapterPopular = AdapterPopular(requireContext(), popularArrayList, this)
        binding.rvPopular.adapter = adapterPopular
    }

    private fun onCoverClicked(admin: Admin) {
        val bundle = Bundle()
        bundle.putSerializable("profileId", admin._id)
        findNavController().navigate(R.id.action_home_to_influencer_profile, bundle)
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.homeInterface = this
        if (arguments?.getString("id") != null) {
            viewModel.getInfluence(arguments?.getString("id")!!)

        } else {
            viewModel.getInfluence("")
        }
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


    override fun onViewPopularClick(admin: Admin) {
        onCoverClicked(admin)
    }

    override fun onHomeAdmins(payload: Payload) {
        progressDialog.dismiss()
        adapterPopular!!.setPopularList(payload.admin)

    }

}