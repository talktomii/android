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
import com.talktomii.R


class FaqActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)
        binding.arrowButton.setOnClickListener {
            if (binding.hiddenView.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(
                    binding.baseCardview,
                    AutoTransition()
                )
                binding.hiddenView.visibility = View.GONE
                binding.arrowButton.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(
                    binding.baseCardview,
                    AutoTransition()
                )
                binding.hiddenView.visibility = View.VISIBLE
                binding.arrowButton.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }
        binding.arrowButton2.setOnClickListener {
            if (binding.hiddenView2.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(
                    binding.baseCardview2,
//                    AutoTransition()
                )
                binding.hiddenView2.visibility = View.GONE
                binding.arrowButton2.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(
                    binding.baseCardview2,
//                    AutoTransition()
                )
                binding.hiddenView2.visibility = View.VISIBLE
                binding.arrowButton2.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }
    }
}