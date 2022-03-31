package com.talktomii.ui.mycards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.talktomii.adapter.MyPaymentAdapter
import com.talktomii.databinding.FragmentPaymentBinding
import com.talktomii.ui.mycards.MyPaymentsVM
import com.talktomii.ui.mycards.PaymentItemsViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PaymentFragment : DaggerFragment() {

    private lateinit var binding: FragmentPaymentBinding

    private val viewModels by viewModels<MyPaymentsVM>()

    @Inject
    lateinit var viewModel: MyPaymentsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = binding.rvPayments
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager

        val dataList = ArrayList<PaymentItemsViewModel>()
        dataList.add(
            PaymentItemsViewModel(
                wallet_name = "Wallet refill",
                wallet_date = "13.02.2022  2:00 PM",
                wallet_price = "-$200,00"
            )
        )
        dataList.add(
            PaymentItemsViewModel(
                wallet_name = "Wallet refill1",
                wallet_date = "13.02.2022  2:00 PM",
                wallet_price = "-$400,00"
            )
        )
        dataList.add(
            PaymentItemsViewModel(
                wallet_name = "Wallet refill2",
                wallet_date = "13.02.2022  3:00 PM",
                wallet_price = "-$200,00"
            )
        )
        dataList.add(
            PaymentItemsViewModel(
                wallet_name = "Wallet refill3",
                wallet_date = "13.02.2022  5:00 PM",
                wallet_price = "-$600,00"
            )
        )
        val adapter = MyPaymentAdapter(dataList)
        recyclerview.adapter = adapter
    }

}