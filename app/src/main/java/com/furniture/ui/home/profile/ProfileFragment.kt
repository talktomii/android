package com.furniture.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.furniture.R
import com.furniture.databinding.EditInterestFragmentBinding
import com.furniture.databinding.FragmentProfileBinding
import com.furniture.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    private lateinit var binding: FragmentProfileBinding


    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvInterest.adapter = AdapterInterests()
        binding.rvAvailability.adapter = AdapterAvailability()

        binding.TextEditProfile.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_editPersonalInfo)
        }

        binding.ivInterest.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_editInterestFragment)
        }

        binding.txtBudgesCount.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_myBudgesFragment)
        }

    }

    companion object {
        const val TAG = "ProfileFragment"
    }

}