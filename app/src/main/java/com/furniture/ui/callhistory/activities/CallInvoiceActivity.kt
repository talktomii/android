package com.furniture.ui.callhistory.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.furniture.R
import com.furniture.databinding.ActivityCallInvoiceBinding
import com.furniture.databinding.ActivityPaymentDetailsBinding
import dagger.android.support.DaggerAppCompatActivity

class CallInvoiceActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityCallInvoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_invoice)

        binding.tvBackCallInvoice.setOnClickListener {
            finish()
        }
    }
}