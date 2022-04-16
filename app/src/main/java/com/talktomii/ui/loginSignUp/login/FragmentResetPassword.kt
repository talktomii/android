package com.talktomii.ui.loginSignUp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.databinding.FragmentResetPasswordBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.utlis.AsteriskPasswordTransformationMethod
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FragmentResetPassword : DaggerFragment() {

    private lateinit var binding: FragmentResetPasswordBinding

    private var isShowPass = false
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

        binding.tvShowHide.setOnClickListener {
            if (isShowPass) {
                binding.tvShowHide.setImageResource(R.drawable.ic_eye)
                binding.edPassword.transformationMethod = AsteriskPasswordTransformationMethod()
                isShowPass = false
            } else {
                binding.tvShowHide.setImageResource(R.drawable.ic_eyeopen)
                binding.edPassword.transformationMethod = null
                isShowPass = true
            }
        }

        binding.txtShowHide.setOnClickListener {
            if (isShowPass) {
                binding.txtShowHide.setImageResource(R.drawable.ic_eye)
                binding.confirmPassword.transformationMethod = AsteriskPasswordTransformationMethod()
                isShowPass = false
            } else {
                binding.txtShowHide.setImageResource(R.drawable.ic_eyeopen)
                binding.confirmPassword.transformationMethod = null
                isShowPass = true
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}