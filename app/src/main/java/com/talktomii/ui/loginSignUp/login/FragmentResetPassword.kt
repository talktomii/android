package com.talktomii.ui.loginSignUp.login

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
import com.talktomii.databinding.FragmentResetPasswordBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.*
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FragmentResetPassword : DaggerFragment() {

    private lateinit var binding: FragmentResetPasswordBinding

    private var isShowPass = false
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
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root


    }

    private fun setListener() {

        binding.btnSubmit.setOnClickListener {
//            findNavController().navigate(R.id.action_resetForget_to_signIn)

            var password = binding.txtNewPass.text.toString()
            var confirmPassword = binding.txtConfirmPass.text.toString()

            if (validation(password,confirmPassword)){

                if (isConnectedToInternet(requireContext(), true)) {
                    var map = HashMap<String, String>()
                    map["email"] = requireArguments()["email"].toString()?:""
                    map["password"] = password

                    viewModel.afterForget(map)
                }

            }else{
                binding.btnSubmit.showSnackBar("password miss match")
            }
        }

        progressDialog = ProgressDialog(requireActivity())

        binding.tvShowHide.setOnClickListener {
            if (isShowPass) {
                binding.tvShowHide.setImageResource(R.drawable.ic_eye)
                binding.txtNewPass.transformationMethod = AsteriskPasswordTransformationMethod()
                isShowPass = false
            } else {
                binding.tvShowHide.setImageResource(R.drawable.ic_eyeopen)
                binding.txtNewPass.transformationMethod = null
                isShowPass = true
            }
        }

        binding.txtShowHide.setOnClickListener {
            if (isShowPass) {
                binding.txtShowHide.setImageResource(R.drawable.ic_eye)
                binding.txtConfirmPass.transformationMethod = AsteriskPasswordTransformationMethod()
                isShowPass = false
            } else {
                binding.txtShowHide.setImageResource(R.drawable.ic_eyeopen)
                binding.txtConfirmPass.transformationMethod = null
                isShowPass = true
            }
        }





    }

    private fun validation(password: String,confirmPassword: String): Boolean {
        return  password==confirmPassword
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setListener()
        bindObservers()
    }

    private fun bindObservers() {

        viewModel.afterForgetPass.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    requireContext().showMessage("New password updated")
                    findNavController().navigate(R.id.action_resetForget_to_signIn)
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