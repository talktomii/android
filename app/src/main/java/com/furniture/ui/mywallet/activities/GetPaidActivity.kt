package com.furniture.ui.mywallet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.furniture.R
import com.furniture.databinding.ActivityGetPaidBinding
import com.furniture.databinding.ActivityRefillWalletBinding
import dagger.android.support.DaggerAppCompatActivity

class GetPaidActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityGetPaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.furniture.R.layout.activity_get_paid)
        binding.tvgetpaidBack.setOnClickListener {
            finish()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListOf("CITY BANK", "CITY BANK", "CITY BANK"))
        binding.typesFilter.setAdapter(adapter)
        binding.typesFilter.setText("CITY BANk")
    }
}