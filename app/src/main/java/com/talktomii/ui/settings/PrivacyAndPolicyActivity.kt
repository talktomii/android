package com.talktomii.ui.settings

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import androidx.databinding.DataBindingUtil
import com.talktomii.R
import com.talktomii.databinding.ActivityFaqBinding
import com.talktomii.databinding.ActivityPrivacyAndPolicyBinding
import dagger.android.support.DaggerAppCompatActivity

class PrivacyAndPolicyActivity : DaggerAppCompatActivity() {


    lateinit var binding: ActivityPrivacyAndPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_and_policy)
        binding.tvBackPrivacy.setOnClickListener {
            finish()
        }

        binding.webview.loadUrl("http://app.talktomii.com/privacy")
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setWebViewClient(WebViewClient())
    }
}