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
import com.talktomii.databinding.FragmentForgetBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.isConnectedToInternet
import com.talktomii.utlis.showSnackBar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ForgetFragment : DaggerFragment() {

    private lateinit var binding: FragmentForgetBinding


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
        binding = FragmentForgetBinding.inflate(inflater, container, false)
        return binding.root



    }

    private fun setListener() {

            binding.txtSubmit.setOnClickListener {
//                findNavController().navigate(R.id.action_forget_to_resetPassword)
                var code = binding.txtVerifyCode.text.toString()

                if (validation(code)){

                    if (isConnectedToInternet(requireContext(), true)) {
                        var map = HashMap<String, String>()
                        map["email"] = requireArguments()["email"].toString()?:""
                        map["code"] = code

                        viewModel.verifyCode(map)

                    }

                }

            }

        binding.txtSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_forget_to_signIn)
            }


        progressDialog = ProgressDialog(requireActivity())
    }

    private fun validation(code: String): Boolean {

        return when{
            code.isEmpty() ->{
                binding.txtVerifyCode.showSnackBar("Please enter verification code")
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

        viewModel.verfyCode.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    findNavController().navigate(R.id.action_forget_to_resetPassword, bundleOf("email" to requireArguments()["email"].toString()))
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