package com.furniture.ui.loginSignUp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.databinding.FragmentForgetPasswordBinding
import com.furniture.databinding.FragmentResetPasswordBinding
import com.furniture.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FragmentResetPassword : DaggerFragment() {

    private lateinit var binding: FragmentResetPasswordBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root

        setListener()
    }

    private fun setListener() {




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}