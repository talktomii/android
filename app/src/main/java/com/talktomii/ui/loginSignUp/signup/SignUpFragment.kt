package com.talktomii.ui.loginSignUp.signup

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
import com.talktomii.databinding.FragmentSignUpBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.*
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject



class SignUpFragment : DaggerFragment() {
    private lateinit var binding: FragmentSignUpBinding


    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var prefsManager: PrefsManager
    val hashMap: HashMap<String, String> = HashMap()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListener()
        setSpannable()
        bindObservers()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_createProfileFragment)
//                bundleOf("email" to binding.txtEmail,"password" to binding.edPassword))

        }
    }


    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
    }

    private fun setSpannable() {
    }

    private fun setListener() {
        binding.tvTermsAndConditions.setOnClickListener {
            val dialog = BackToHomeDialog(this)
            dialog.show(requireActivity().supportFragmentManager, BackToHomeDialog.TAG)
        }
    }

//    private fun validation(number: String, email: String): Boolean {
//        return when {
//            number.isEmpty() -> {
//                binding.etMobile.showSnackBar(getString(R.string.validation_number))
//                false
//            }
//            number.length < 6 -> {
//                binding.etMobile.showSnackBar(getString(R.string.validation_number_lenght))
//                false
//            }
//            email.isEmpty() -> {
//                binding.etEmail.showSnackBar(getString(R.string.validation_email))
//                false
//            }
//            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
//                binding.etEmail.showSnackBar(getString(R.string.validation_email_address))
//                false
//            }
//            else -> true
//        }
//    }


    private fun bindObservers() {
        viewModel.getSendOtp.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    requireContext().showMessage(getString(R.string.otp_send_successful))
                    val bundle = Bundle()
                    bundle.putSerializable(HASHMAP_KEY, hashMap)
//                    findNavController().navigate(R.id.otpFragment, bundle)
                    viewModel.getSendOtp.value = null
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