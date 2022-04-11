package com.talktomii.ui.FAQ

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.databinding.DataBindingUtil

import com.talktomii.databinding.ActivityFaqBinding
import com.talktomii.databinding.ActivityPaymentDetailsBinding
import dagger.android.support.DaggerAppCompatActivity
import androidx.cardview.widget.CardView

import android.widget.LinearLayout

import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.R
import com.zoho.salesiqembed.ZohoSalesIQ


class FaqActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)
        binding.tvFaqBack.setOnClickListener {
            ZohoSalesIQ.showLauncher(false)
        }
        ZohoSalesIQ.showLauncher(true)
        val dataList = ArrayList<FaqModel>()
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        binding.rvfaq.layoutManager = layoutManager
        dataList.add(FaqModel(getString(R.string.q1),getString(R.string.a1)))
        dataList.add(FaqModel(getString(R.string.q2),getString(R.string.a2)))
        dataList.add(FaqModel(getString(R.string.q3),getString(R.string.a3)))
        dataList.add(FaqModel(getString(R.string.q4),getString(R.string.a4)))
        dataList.add(FaqModel(getString(R.string.q5),getString(R.string.a5)))
        dataList.add(FaqModel(getString(R.string.q6),getString(R.string.a6)))
        dataList.add(FaqModel(getString(R.string.q7),getString(R.string.a7)))
        dataList.add(FaqModel(getString(R.string.q8),getString(R.string.a8)))
        dataList.add(FaqModel(getString(R.string.q9),getString(R.string.a9)))
        dataList.add(FaqModel(getString(R.string.q10),getString(R.string.a10)))
        dataList.add(FaqModel(getString(R.string.q11),getString(R.string.a11)))
        dataList.add(FaqModel(getString(R.string.q12),getString(R.string.a12)))
        dataList.add(FaqModel(getString(R.string.q13),getString(R.string.a13)))
        dataList.add(FaqModel(getString(R.string.q14),getString(R.string.a14)))
        dataList.add(FaqModel(getString(R.string.q15),getString(R.string.a15)))
        dataList.add(FaqModel(getString(R.string.q16),getString(R.string.a16)))
        dataList.add(FaqModel(getString(R.string.q17),getString(R.string.a17)))
        dataList.add(FaqModel(getString(R.string.q18),getString(R.string.a18)))
        dataList.add(FaqModel(getString(R.string.q19),getString(R.string.a19)))
        dataList.add(FaqModel(getString(R.string.q20),getString(R.string.a20)))
        dataList.add(FaqModel(getString(R.string.q21),getString(R.string.a21)))
        dataList.add(FaqModel(getString(R.string.q22),getString(R.string.a22)))
        val adapter = FAQAdapter(dataList)
        binding.rvfaq.adapter = adapter
        ZohoSalesIQ.showLauncher(true)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        ZohoSalesIQ.showLauncher(false)
    }
}