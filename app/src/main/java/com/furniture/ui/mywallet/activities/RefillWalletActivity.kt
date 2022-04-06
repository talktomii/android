package com.furniture.ui.mywallet.activities

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.furniture.databinding.ActivityRefillWalletBinding
import dagger.android.support.DaggerAppCompatActivity

class RefillWalletActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityRefillWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.furniture.R.layout.activity_refill_wallet)
        binding.tvrefillBack.setOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrayListOf("53****1234", "53****1234", "53****1234"))
        binding.typesFilter.setAdapter(adapter)
        binding.typesFilter.setText("53****1234")
    }
}