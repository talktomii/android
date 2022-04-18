package com.talktomii.ui.loginSignUp.login

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.databinding.FragmentForgetPasswordBinding
import com.talktomii.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ForgetPasswordFragment : DaggerFragment() {

    private lateinit var binding: FragmentForgetPasswordBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.backPass.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.backPass.setImageResource(R.drawable.back_arrow_light)
            }
        }
        return binding.root

    }

    private fun setListener() {

        binding.txtResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPassword_to_forget)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }
}