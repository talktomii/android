package com.furniture.ui.mycards.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.adapter.MyCardAdapter
import com.furniture.data.apis.WebService
import com.furniture.data.network.responseUtil.ApiResponse
import com.furniture.data.network.responseUtil.ApiUtils
import com.furniture.data.network.responseUtil.Resource
import com.furniture.databinding.FragmentCardBinding
import com.furniture.databinding.FragmentHomeBinding
import com.furniture.di.SingleLiveEvent
import com.furniture.ui.mycards.CardItemsViewModel
import com.furniture.ui.mycards.MyCardsVM
import com.furniture.ui.mycards.data.MyCardsViewModel
import com.furniture.ui.mycards.data.getCardDetail
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import android.content.SharedPreferences




class CardFragment : DaggerFragment(R.layout.fragment_card) {

    private lateinit var binding: FragmentCardBinding
    private val viewModels by viewModels<MyCardsVM>()

    @Inject
    lateinit var viewModel: MyCardsVM
    val cards by lazy { SingleLiveEvent<Resource<getCardDetail>>() }
    @Inject
    lateinit var dataModel: MyCardsViewModel
    lateinit var webService : WebService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)
//        binding.vm = viewModels
        recycleview = binding.rvCards
        dataModel.getCards()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object{
        lateinit var recycleview: RecyclerView
    }
}