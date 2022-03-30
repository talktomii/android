package com.talktomii.ui.mywallet.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.talktomii.databinding.ActivityGetPaidBinding
import dagger.android.support.DaggerAppCompatActivity

class GetPaidActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityGetPaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, com.talktomii.R.layout.activity_get_paid)
        binding.tvgetpaidBack.setOnClickListener {
            finish()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListOf("CITY BANK", "CITY BANK", "CITY BANK"))
        binding.typesFilter.setAdapter(adapter)
        binding.typesFilter.setText("CITY BANk")
    }
}