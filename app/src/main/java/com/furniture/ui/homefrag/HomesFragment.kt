package com.furniture.ui.homefrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.databinding.HomeFragmentBinding
import com.furniture.ui.home.HomeScreenViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomesFragment : DaggerFragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding
    private val viewModels by viewModels<HomeScreenViewModel>()

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

        binding.rvPopular.adapter = AdapterPopular(this)
    }

    fun onCoverClicked() {
        findNavController().navigate(R.id.action_home_to_influencer_profile)
    }

    fun init() {
        if (arguments?.getString("id") != null) {
            viewModel.getInfluence(arguments?.getString("id")!!)

        } else {
            viewModel.getInfluence("")
        }
    }

}