package com.furniture.ui.loginSignUp.login

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
import com.furniture.R
import com.furniture.data.model.UserData
import com.furniture.databinding.FragmentLoginBinding
import com.furniture.ui.loginSignUp.LoginViewModel
import com.furniture.utlis.PrefsManager
import com.furniture.utlis.dialogs.ProgressDialog
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
        initializeGoogle()

        setListener()
        setSpannable()
//        bindObservers()

        faceBookLogin()
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
    }

    var instaIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val user = data?.getParcelableExtra<UserData>("userData")

                val map = HashMap<String, String>()
                map["social_id"] = (user?.id.toString())
                map["device_token"] = deviceToken ?: ""
                map["device_type"] = "ANDROID"
                map["name"] = user?.username ?: ""
                map["social_type"] = "instagram"
//                viewModel.socialLogin(map)
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

    fun getGoogleAccountInfo(completedTask: Task<GoogleSignInAccount>) {
        val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
        println("Google detail:  $account")
        var imageUrl = ""
        if (account.photoUrl != null) imageUrl = URL(account.photoUrl.toString()).toString()

        val map = HashMap<String, String>()
        map["social_id"] = account.id ?: ""
        map["device_token"] = deviceToken ?: ""
        map["email"] = account.email ?: ""
        map["name"] = account.displayName ?: ""
        //map["avatar"] = imageUrl
        map["social_type"] = "google"

//        viewModel.socialLogin(map)
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

    fun faceBookLogin() {
        callbackManager = CallbackManager.Factory.create()

//        binding.fbLoginButton.registerCallback(
//            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val request =
                        GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { data, _ ->
                            try {
                                println("-------- ${data}")
                                val map = HashMap<String, String>()

                                map["social_id"] = data.getString("id")
                                map["device_token"] = deviceToken ?: ""
                                map["email"] = if (data.has("email"))
                                    data.getString("email") else data.getString("id") + "@facebook.com"
                                map["name"] = data.getString("name")

                                /* if (data.has("picture")) {
                                     map["avatar"] = data.getJSONObject("picture").getJSONObject("data")
                                         .getString("url")
                                 }*/
                                map["device_type"] = "ANDROID"
                                map["social_type"] = "facebook"
//                                viewModel.socialLogin(map)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id, name, link, picture.type(large), email, gender,first_name,last_name"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    print("---cancle--")
                }

                override fun onError(exception: FacebookException?) {
                    exception?.stackTrace
                }
            }
    }

    private fun setListener() {
        binding.txtSignUp.setOnClickListener {

        }
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


//    private fun bindObservers() {
//        viewModel.getSendOtp.observe(requireActivity(), Observer {
//            it ?: return@Observer
//            when (it.status) {
//                Status.SUCCESS -> {
//                    progressDialog.setLoading(false)
//                    requireContext().showMessage(getString(R.string.otp_send_successful))
//                    val bundle = Bundle()
//                    bundle.putSerializable(HASHMAP_KEY, hashMap)
//                    findNavController().navigate(R.id.otpFragment, bundle)
//                    viewModel.getSendOtp.value = null
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

//        viewModel.verifyOTP.observe(requireActivity(), Observer {
//            it ?: return@Observer
//            when (it.status) {
//
//                Status.SUCCESS -> {
//
//                    progressDialog.setLoading(false)
//
//                    prefsManager.save(PrefsManager.PREF_API_TOKEN, it.data?.token)
//
//                    prefsManager.save(PrefsManager.PREF_PROFILE, it.data)
//
//
//                    if (it.data?.phone_no == null) {
//
//                        findNavController().navigate(R.id.enterMobileFragment)
//                    } else {
//                        val intent = Intent(context, HomeActivity::class.java)
//                        startActivity(intent)
//                    }
//                }
//
//                Status.ERROR -> {
//                    progressDialog.setLoading(false)
//
//                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
//                }
//                Status.LOADING -> {
//
//                    progressDialog.setLoading(true)
//                }
//
//            }
//        })
//    }


}