package com.talktomii.ui.reportabuse

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.talktomii.R
import com.talktomii.databinding.ActivityReportAbuseBinding
import dagger.android.support.DaggerAppCompatActivity

class ReportAbuseActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityReportAbuseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_abuse)

        binding.tvBackReportAbuse.setOnClickListener {
            finish()
        }
    }
}