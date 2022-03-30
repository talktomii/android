package com.talktomii.ui.homefrag

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.databinding.HomeFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomesFragment : DaggerFragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding
    private val viewModels by viewModels<HomeVM>()

    @Inject
    lateinit var viewModel: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModels

        binding.rvPopular.adapter = AdapterPopular(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
    }

    fun onCoverClicked() {
        findNavController().navigate(R.id.action_home_to_influencer_profile)
    }

}