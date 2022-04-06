package com.furniture.ui.mycards.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.furniture.R
import com.furniture.databinding.ActivityMyCardsBinding
import com.furniture.databinding.ActivityPaymentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.android.support.DaggerAppCompatActivity

class PaymentDetailsActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityPaymentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_details)
        binding.headerLayout.setOnClickListener {
            finish()
        }
        binding.repeatPaymentLayout.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottomsheet_refund_payment, null)
            val btnClose = view.findViewById<ImageView>(R.id.btnCloseSheet)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }
    }
}