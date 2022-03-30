package com.furniture.ui.homefrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.data.model.admin.Admin
import com.furniture.data.model.admin.Payload
import com.furniture.databinding.HomeFragmentBinding
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.HomeInterface
import com.furniture.ui.home.HomeScreenViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomesFragment : DaggerFragment(R.layout.home_fragment), HomeInterface, CommonInterface {

    private lateinit var binding: HomeFragmentBinding
    private val viewModels by viewModels<HomeScreenViewModel>()
    private var adapterPopular: AdapterPopular? = null
    private var popularArrayList: ArrayList<Admin> = arrayListOf()

    @Inject
    lateinit var viewModel: HomeScreenViewModel

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
        adapterPopular = AdapterPopular(this,popularArrayList)
        binding.rvPopular.adapter = adapterPopular
    }

    fun onCoverClicked() {
        findNavController().navigate(R.id.action_home_to_influencer_profile)
    }

    fun init() {
        viewModel.commonInterface = this
        viewModel.homeInterface = this
        if (arguments?.getString("id") != null) {
            viewModel.getInfluence(arguments?.getString("id")!!)

        } else {
            viewModel.getInfluence("")
        }
    }

    override fun onFailure(message: String) {

    }

    override fun onStarted() {
    }

    override fun onHomeAdmins(payload: Payload) {
//        popularArrayList.addAll(payload.admin)
        adapterPopular!!.setPopularList(payload.admin)
    }

}