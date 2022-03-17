package com.furniture.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.furniture.R
import com.furniture.data.network.ApisRespHandler
import com.furniture.data.network.Config
import com.furniture.data.network.responseUtil.Status
import com.furniture.databinding.ActivityPaymentWebBinding
import com.furniture.ui.loginSignUp.LoginViewModel
import com.furniture.utlis.LocaleHelper
import com.furniture.utlis.PrefsManager
import com.furniture.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class InstaWebActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityPaymentWebBinding
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var prefsManager: PrefsManager

    var TAG = "INSTA"
    val url = ("https://api.instagram.com/oauth/authorize/?client_id=" +
            Config.INSTAGRAM_CLIENT_ID
            + "&redirect_uri="
            + Config.REDIRECT_URL
            + "&response_type=code"
            + "&scope=user_profile")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_web)
        LocaleHelper.setLocale(this, prefsManager.getString(PrefsManager.LOCAL, "en"), prefsManager)
        initialise()
        observeData()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initialise() {
        progressDialog = ProgressDialog(this)



        binding.webView.webViewClient = WebViewClient()

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setAppCacheEnabled(true)
        binding.webView.settings.domStorageEnabled = true

        binding.webView.settings.useWideViewPort = true

        binding.progressWebView.max = 100

        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        binding.webView.settings.builtInZoomControls = true

        binding.webView.loadUrl(url)

        binding.webView.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }


            //https://dev.tsaom.com/instagram/callback?code=AQB9qfKhRhQT84DDvKNmcnjrxopba5vOqdsrbERT6C-bYAQsYcVpSKkYknvRzVRX2_LMIkLh5IyBm1NpTfYhnKtUen4Tapbx4aG4ofSrAXCjQSPg6si7A9gikkhd7mkpJt1KO5LWVMrgfQcPIkJQQmJHPBJPEMPmQM7YXemr2-rejes6YuVk5f6qiZkeKvF8FtHbL9HEXHn38jEUZtA_Cp-K5k1ONz7D06Y5JSMAMINlSw#_
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                println("+++++++++++++++++++++333  " + url)
                Log.d(TAG, "onPageFinished called")
                if (url.contains("callback?code")) {
                    var code = url.split("code=")[1]
                    Log.d(TAG, "token: $code")
                    getAccessToken(code)
                } else if (url.contains("?error")) {
                    Log.d(TAG, "getting error fetching access token")
                } else {
                    Log.d(TAG, "outside both$url")
                }
            }
        })
    }


    fun observeData() {
        viewModel.insta.observe(this, Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    val fields = "id,profile_picture,username"
                    viewModel.apigetInstaUser(
                        it.data?.user_id.toString(),
                        fields,
                        it.data?.access_token ?: ""
                    )
                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, this, prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                    ApisRespHandler.handleError(it.error, this, prefsManager)
                }
            }
        })


        viewModel.instaUser.observe(this, Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    val intent = Intent()
                    intent.putExtra("userData", it.data)
                    setResult(RESULT_OK, intent)
                    finish()
                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, this, prefsManager)
                }
                Status.LOADING -> {

                }
            }
        })
    }


    fun getAccessToken(code: String) {
        val newCode = code.replace("#_", "")
        val hashmap = HashMap<String, String>()
        hashmap["client_id"] = Config.INSTAGRAM_CLIENT_ID
        hashmap["client_secret"] = Config.INSTAGRAM_SECRET_ID
        hashmap["grant_type"] = "authorization_code"
        hashmap["redirect_uri"] = Config.REDIRECT_URL
        hashmap["code"] = newCode

        viewModel.apiInstaUser(hashmap)

    }

}