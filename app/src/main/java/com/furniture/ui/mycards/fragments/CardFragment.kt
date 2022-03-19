package com.furniture.ui.mycards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.furniture.R
import com.furniture.adapter.MyCardAdapter
import com.furniture.databinding.FragmentCardBinding
import com.furniture.databinding.FragmentHomeBinding
import com.furniture.ui.mycards.CardItemsViewModel
import com.furniture.ui.mycards.MyCardsVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CardFragment : DaggerFragment(R.layout.fragment_card) {

    private lateinit var binding: FragmentCardBinding
    private val viewModels by viewModels<MyCardsVM>()

    @Inject
    lateinit var viewModel: MyCardsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)
//        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = binding.rvCards
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager
        val dataList = ArrayList<CardItemsViewModel>()
        dataList.add(
            CardItemsViewModel(
                card_Number = "53****1234",
                card_Img = R.drawable.master_card1
            )
        )
        dataList.add(
            CardItemsViewModel(
                card_Number = "53****0000",
                card_Img = R.drawable.master_card1
            )
        )
        dataList.add(
            CardItemsViewModel(
                card_Number = "53****1234",
                card_Img = R.drawable.visa_card
            )
        )
        dataList.add(
            CardItemsViewModel(
                card_Number = "23****1234",
                card_Img = R.drawable.visa_card
            )
        )
        val adapter = MyCardAdapter(dataList)
        recyclerview.adapter = adapter
    }


}