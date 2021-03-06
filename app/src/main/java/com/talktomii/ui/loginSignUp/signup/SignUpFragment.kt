package com.talktomii.ui.loginSignUp.signup

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.talktomii.R
import com.talktomii.databinding.FragmentSignUpBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.utlis.*
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import org.json.JSONException
import java.net.URL
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class SignUpFragment : DaggerFragment() {
    private lateinit var binding: FragmentSignUpBinding

    private var isShowPass = false

    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private var callbackManager: CallbackManager? = null

    @Inject
    lateinit var prefsManager: PrefsManager
    val hashMap: HashMap<String, String> = HashMap()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
// Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivGoogle.setImageResource(R.drawable.googe_btn)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivGoogle.setImageResource(R.drawable.google_btn_light)
            }
        }
        val text = requireContext().resources.getString(R.string.terms_policy)
        val ss = SpannableString(text)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.talktomii.com/terms"))
                startActivity(browserIntent)
            }
        }

        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.talktomii.com/privacy"))
                startActivity(browserIntent)
            }
        }

        ss.setSpan(clickableSpan1, 32, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(ForegroundColorSpan(Color.parseColor("#55ADFF")), 32, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpan2, 52, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(ForegroundColorSpan(Color.parseColor("#55ADFF")), 52, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvTermsAndConditions.text = ss
        binding.tvTermsAndConditions.movementMethod = LinkMovementMethod.getInstance()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbackManager = CallbackManager.Factory.create()

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivGoogle.setImageResource(R.drawable.googe_btn)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivGoogle.setImageResource(R.drawable.google_btn_light)
            }
        }
        init()
        setListener()
        setSpannable()
        bindObservers()
        initializeGoogle()

        binding.btnNext.setOnClickListener {

            val email = binding.txtEmailId.text.toString()
            val password = binding.edPassword.text.toString()
            val repeatPassword = binding.repPassword.text.toString()
            if (validation(email, password, repeatPassword))
                findNavController().navigate(
                    R.id.action_signupFragment_to_createProfileFragment,
                    bundleOf(
                        "email" to binding.txtEmailId.text.toString(),
                        "password" to binding.edPassword.text.toString()
                    )
                )
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
        val permissionNeeds: List<String> = Arrays.asList(
            "email",
            "public_profile"
        )
        binding.fbLoginButton.setReadPermissions(permissionNeeds)
        binding.fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                val request =
                    GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { data, _ ->
                        try {
                            var  profile = Profile.getCurrentProfile();
                            Log.e("email", data!!.getString("email"))
                            findNavController().navigate(
                                R.id.action_signupFragment_to_createProfileFragment,
                                bundleOf(

                                    "email" to data?.getString("email"),
                                    "isSocial" to "true"
                                )
                            )

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email")

                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                print("---cancle--")
            }

            override fun onError(exception: FacebookException) {
                exception?.stackTrace
            }
        })
    }

    private fun setSpannable() {

    }

    private fun setListener() {

        binding.ivFacebook.setOnClickListener {
            binding.fbLoginButton.performClick()
        }

//        binding.tvTermsAndConditions.setOnClickListener {
//            val dialog = TermsAndConditionsDialog(this)
//            dialog.show(requireActivity().supportFragmentManager, TermsAndConditionsDialog.TAG)
//        }

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

    private fun validation(email: String, password: String, repeatPassword: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.txtEmailId.showSnackBar("please enter email id ")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.txtEmailId.showSnackBar("Please enter a valid email address")
                false
            }
            password.isEmpty() -> {
                binding.edPassword.showSnackBar("please enter password")
                false
            }
            repeatPassword.isEmpty() -> {
                binding.repPassword.showSnackBar("please retype your password")
                false
            }
//            repeatPassword==password ->{
//                binding.repPassword.showSnackBar("Password doesn't match")
//
//                false
//            }

            else -> true

        }

    }

    fun initializeGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        mGoogleSignInClient.signOut()
    }


    var googleResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task != null) getGoogleAccountInfo(task)
            }
        }


    private fun getGoogleAccountInfo(completedTask: Task<GoogleSignInAccount>) {

        val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
        Log.e("Google detail: ", " $account")
        var imageUrl = ""
        if (account.photoUrl != null) imageUrl = URL(account.photoUrl.toString()).toString()
        findNavController().navigate(
            R.id.action_signupFragment_to_createProfileFragment,
            bundleOf(

                "email" to account.email,
                "isSocial" to "true"
            )
        )
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


    }
}