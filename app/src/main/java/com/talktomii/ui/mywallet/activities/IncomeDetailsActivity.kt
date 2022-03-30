package com.talktomii.ui.mywallet.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.talktomii.R
import com.talktomii.databinding.ActivityIncomeDetailsBinding
import dagger.android.support.DaggerAppCompatActivity

class IncomeDetailsActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityIncomeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_income_details)
    }
}