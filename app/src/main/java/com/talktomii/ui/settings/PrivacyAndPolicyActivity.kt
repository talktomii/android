package com.talktomii.ui.settings

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.talktomii.R
import com.talktomii.databinding.ActivityPrivacyAndPolicyBinding
import dagger.android.support.DaggerAppCompatActivity
import im.delight.android.webview.AdvancedWebView


class PrivacyAndPolicyActivity : DaggerAppCompatActivity(), AdvancedWebView.Listener {


    lateinit var binding: ActivityPrivacyAndPolicyBinding
    private val DESKTOP_USER_AGENT =
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_and_policy)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.backToPrivacy.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.backToPrivacy.setImageResource(R.drawable.back_arrow_light)
            }
        }
        binding.tvBackPrivacy.setOnClickListener {
            finish()
        }
        initWebView()
    }


    private fun initWebView() {
        binding.webview.setListener(this, this)
        binding.webview.setMixedContentAllowed(false)
        binding.webview.loadUrl("https://app.talktomii.com/privacy/")
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {

    }

    override fun onPageFinished(url: String?) {
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
    }

    override fun onExternalPageRequest(url: String?) {
    }
}