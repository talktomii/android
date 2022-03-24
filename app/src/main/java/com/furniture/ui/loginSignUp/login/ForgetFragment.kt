package com.furniture.ui.loginSignUp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.databinding.FragmentForgetBinding
import com.furniture.databinding.FragmentMyBadgesBinding
import com.furniture.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import javax.inject.Inject

class ForgetFragment : DaggerFragment() {

    private lateinit var binding: FragmentForgetBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgetBinding.inflate(inflater, container, false)
        return binding.root



    }

    private fun setListener() {

            binding.txtCheckInbox.setOnClickListener {
                findNavController().navigate(R.id.action_forget_to_resetPassword)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }
}