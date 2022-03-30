package com.talktomii.ui.loginSignUp.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginResult
import com.talktomii.R
import com.talktomii.data.model.UserData
import com.talktomii.databinding.FragmentLoginBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.support.DaggerFragment
import org.json.JSONException
import java.net.URL
import javax.inject.Inject


class LoginFragment : DaggerFragment() {
    lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var prefsManager: PrefsManager

    private val hashMap: HashMap<String, String> = HashMap()


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
//        initializeGoogle()

        setListener()
        setSpannable()
//        bindObservers()

//        faceBookLogin()
        getDeviceToken()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setListener() {
//        binding.txtSignUp.setOnClickListener {
//
//        }
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