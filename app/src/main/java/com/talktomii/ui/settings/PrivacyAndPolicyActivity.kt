package com.talktomii.ui.settings

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.talktomii.R
import com.talktomii.databinding.ActivityFaqBinding
import com.talktomii.databinding.ActivityPrivacyAndPolicyBinding
import com.thefinestartist.finestwebview.FinestWebView
import dagger.android.support.DaggerAppCompatActivity
import com.itextpdf.text.pdf.PdfFileSpecification.url




class PrivacyAndPolicyActivity : DaggerAppCompatActivity() {


    lateinit var binding: ActivityPrivacyAndPolicyBinding
    private val DESKTOP_USER_AGENT =
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_and_policy)
        binding.tvBackPrivacy.setOnClickListener {
            finish()
        }
        binding.webview.setWebViewClient(MyWebViewClient(this))
        val url = "https://app.talktomii.com/privacy/"
        binding.webview.getSettings().setJavaScriptEnabled(true)
        binding.webview.loadUrl(url) // load the url on the web view
    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}