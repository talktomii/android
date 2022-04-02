package com.talktomii.ui.coupon

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.talktomii.R
import com.talktomii.databinding.ActivityCouponBinding
import com.talktomii.ui.mycards.activities.MyCardsActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class CouponActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityCouponBinding

    @Inject
    lateinit var viewModel: MyCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        getContext(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon)
        progress = binding.addCouponProgress
        layout = binding.couponayout
        binding.tvCouponBack.setOnClickListener {
            finish()
        }
//        binding.etCouponCode.setText("6247f9a2483f24ab5093f3e6")
        binding.btnAddCoupon.setOnClickListener {
            if(binding.etCouponCode.text!!.length <= 0){
                val snackbar = Snackbar.make(
                    layout,
                    "Enter Coupon Code",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }else{
                viewModel.addCoupon(binding.etCouponCode.text.toString().trim())
            }
        }
        binding.etCouponCode.requestFocus()
    }

    companion object{
        lateinit var progress : ProgressBar
        lateinit var layout: RelativeLayout
        lateinit var context : Context
        fun getContext(context: Context){
            this.context = context
        }
        fun finishFunction() {
            Handler().postDelayed({
                val activity = context as Activity
                activity.finish()
            },1000)

        }
    }
}