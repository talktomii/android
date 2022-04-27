package com.talktomii.ui.loginSignUp.login

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.FragmentForgetPasswordBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.isConnectedToInternet
import com.talktomii.utlis.showMessage
import com.talktomii.utlis.showSnackBar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ForgetPasswordFragment : DaggerFragment() {

    private lateinit var binding: FragmentForgetPasswordBinding


    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    private lateinit var progressDialog: ProgressDialog


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
//            findNavController().navigate(R.id.action_forgetPassword_to_forget,)
            var email = binding.txtEmail.text.toString()

            if (validation(email)){

                if (isConnectedToInternet(requireContext(), true)) {
                    var map = HashMap<String, String>()
                    map["email"] = email

                    viewModel.verifyEmail(map)

                }

            }
        }
        progressDialog = ProgressDialog(requireActivity())
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun validation(email: String): Boolean {

        return when{
            email.isEmpty() ->{
                binding.txtEmail.showSnackBar("Please enter your email id")
                false
            }
            else -> true

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        bindObservers()
    }

    private fun bindObservers() {

        viewModel.verify.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    findNavController().navigate(R.id.action_forgetPassword_to_forget, bundleOf("email" to binding.txtEmail.text.toString()))
                }

                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }

            }
        })
    }
}

