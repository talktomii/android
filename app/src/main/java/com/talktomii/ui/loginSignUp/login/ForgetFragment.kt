package com.talktomii.ui.loginSignUp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.databinding.FragmentForgetBinding
import com.talktomii.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
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

            binding.txtSubmit.setOnClickListener {
                findNavController().navigate(R.id.action_forget_to_resetPassword)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }
}