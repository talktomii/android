package com.furniture.ui.mywallet.activities

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.furniture.databinding.ActivityRefillWalletBinding
import dagger.android.support.DaggerAppCompatActivity

class RefillWalletActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityRefillWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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