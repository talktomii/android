package com.furniture.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.databinding.SearchFragmentBinding
import com.furniture.ui.home.HomeViewModel
import com.furniture.ui.home.profile.AdapterInterests
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment: DaggerFragment() {

    private lateinit var binding: SearchFragmentBinding


    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategories.adapter = AdapterCategories(requireContext())

    }
}