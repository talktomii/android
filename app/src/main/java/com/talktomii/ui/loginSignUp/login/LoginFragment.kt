package com.talktomii.ui.loginSignUp.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.talktomii.R
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.FragmentLoginBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class LoginFragment : DaggerFragment() {
    lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var prefsManager: PrefsManager

    private val hashMap: HashMap<String, String> = HashMap()

    private var isShowPass = false

    private var callbackManager: CallbackManager? = null

    var deviceToken: String? = ""

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initializeGoogle()

        setListener()
        setSpannable()
        bindObservers()

//        faceBookLogin()
        getDeviceToken()
    }

    private fun bindObservers() {

        viewModel.login.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    prefsManager.save(PrefsManager.PREF_API_TOKEN, it.data?.token)
                    prefsManager.save(PrefsManager.PREF_PROFILE, it.data)
                    if (it.data?.admin?.role?.roleName == "user")
                        findNavController().navigate(R.id.homeFragment)
                    else
                        findNavController().navigate(R.id.homeInfluencerFragment)
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

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
    }

    fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                deviceToken = it.result.toString()
                Log.e("DeviceToken", deviceToken ?: "")
            }
        }
    }

    private fun setSpannable() {
        binding.ivFacebook.setOnClickListener {

        }

        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }

        binding.txtForgetPass.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_forgetPassword)
        }
        binding.ivGoogle.setOnClickListener {
            googleResultLauncher.launch(mGoogleSignInClient.signInIntent)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_login_to_home)
            var email = binding.txtEmailId.text.toString()
            var password = binding.edPassword.text.toString()

            var map = HashMap<String, String>()
            map["email"] = email
            map["password"] = password

            viewModel.loginApi(map)
        }

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

    private fun getGoogleAccountInfo(task: Task<GoogleSignInAccount>) {

    }

//    private fun validation(number: String): Boolean {
//        return when {
//            number.isEmpty() -> {
//                binding.etMobile.showSnackBar(getString(R.string.validation_number))
//                false
//            }
//            number.length < 6 -> {
//                binding.etMobile.showSnackBar(getString(R.string.validation_number_lenght))
//                false
//            }
//
//            else -> true
//        }
//    }

}