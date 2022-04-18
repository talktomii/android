package com.talktomii.ui.loginSignUp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ForgetPasswordFragment : DaggerFragment() {

    private lateinit var binding: FragmentForgetPasswordBinding


    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun setListener() {

        binding.txtResetPassword.setOnClickListener {
//            findNavController().navigate(R.id.action_forgetPassword_to_forget)

        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun validation(email: String): Boolean {

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        bindObservers()
    }

    private fun bindObservers() {

//        viewModel.verify.observe(requireActivity(), Observer {
//            it ?: return@Observer
//            when (it.status) {
//                Status.SUCCESS -> {
//                    progressDialog.setLoading(false)
//
//                }
//
//                Status.ERROR -> {
//                    progressDialog.setLoading(false)
//                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
//                }
//                Status.LOADING -> {
//                    progressDialog.setLoading(true)
//                }
//
//            }
//        })
//    }
}

}