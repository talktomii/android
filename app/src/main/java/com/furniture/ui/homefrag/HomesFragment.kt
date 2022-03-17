package com.furniture.ui.homefrag

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.furniture.R
import com.furniture.databinding.HomeFragmentBinding
import com.furniture.ui.editpersonalinfo.EditPersonalInfoVM
import com.furniture.ui.home.profile.AdapterInterests
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

        binding.rvPopular.adapter = AdapterPopular()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
    }

}