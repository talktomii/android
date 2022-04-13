package com.talktomii.ui.mycards.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.adapter.MyPaymentAdapter
import com.talktomii.databinding.FragmentPaymentBinding
import com.talktomii.ui.mycards.MyPaymentsVM
import com.talktomii.ui.mycards.PaymentItemsViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PaymentFragment : DaggerFragment() {

    private lateinit var binding: FragmentPaymentBinding

    private val viewModels by viewModels<MyPaymentsVM>()

    @Inject
    lateinit var viewModel: MyPaymentsVM

    @Inject
    lateinit var dataModel: MyCardsViewModel
    lateinit var webService : WebService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.saerchPayment.setBackgroundResource(R.drawable.bgscancard_rounder_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.saerchPayment.background = resources.getDrawable(R.drawable.bg_scan_card_rounder)
            }
        }
        recycleview = binding.rvPayments
        progress = binding.displayPaymentProgress
        progress.visibility  = View.VISIBLE
        recycleview.visibility = View.GONE
        dataModel.getPayment()
        return binding.root
    }

    companion object{
        lateinit var recycleview: RecyclerView
        lateinit var progress : ProgressBar
    }
}