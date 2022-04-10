package com.talktomii.ui.loginSignUp.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.talktomii.R
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.FragmentSignUpBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.*
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import java.net.URL
import javax.inject.Inject


class SignUpFragment : DaggerFragment() {
    private lateinit var binding: FragmentSignUpBinding

    private var isShowPass = false
    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    lateinit var mGoogleSignInClient: GoogleSignInClient

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
        initializeGoogle()

        binding.btnNext.setOnClickListener {

            val email = binding.txtEmailId.text.toString()
            val password = binding.edPassword.text.toString()
            if (validation(email, password))
                if (binding.chckTerms.isChecked) {
                    findNavController().navigate(
                        R.id.action_signupFragment_to_createProfileFragment,
                        bundleOf(
                            "email" to binding.txtEmailId.text.toString(),
                            "password" to binding.edPassword.text.toString()
                        )
                    )
                }
                else {
                    binding.chckTerms.showSnackBar("Please accept our terms & conditions")
                }
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

        binding.txtSignIn.setOnClickListener {
//            findNavController().navigate(R.id.action_signupFragment_to_signIn)
            requireActivity().onBackPressed()
        }

        binding.ivGoogle.setOnClickListener {
            googleResultLauncher.launch(mGoogleSignInClient.signInIntent)
        }

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
                binding.repPassword.transformationMethod = AsteriskPasswordTransformationMethod()
                isShowPass = false
            } else {
                binding.txtShowHide.setImageResource(R.drawable.ic_eyeopen)
                binding.repPassword.transformationMethod = null
                isShowPass = true
            }
        }
    }

    private fun validation(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.txtEmailId.showSnackBar("please enter email id ")
                false
            }
            password.isEmpty() -> {
                binding.edPassword.showSnackBar("please enter password")
                false
            }
            else -> true

        }

    }

    fun initializeGoogle() {

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        mGoogleSignInClient.signOut()
    }


    var googleResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task != null) getGoogleAccountInfo(task)
            }
        }


    private fun getGoogleAccountInfo(completedTask: Task<GoogleSignInAccount>) {

        val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
        println("Google detail:  $account")
        var imageUrl = ""
        if (account.photoUrl != null) imageUrl = URL(account.photoUrl.toString()).toString()


    }


    private fun bindObservers() {
//        viewModel.role.observe(requireActivity(), Observer {
//            it ?: return@Observer
//            when (it.status) {
//                Status.SUCCESS -> {
//                    progressDialog.setLoading(false)
//                    prefsManager.save(PrefsManager.PREF_API_TOKEN, it.data)
//                    prefsManager.save(PrefsManager.PREF_PROFILE, it.data)
//                    binding.rvRole.adapter = AdapterRole(it.data?.allRole?: arrayListOf(),this)
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


    }}