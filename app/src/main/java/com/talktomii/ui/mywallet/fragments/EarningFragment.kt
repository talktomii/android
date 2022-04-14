package com.talktomii.ui.mywallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.FragmentEarningBinding
import com.talktomii.ui.mywallet.adapters.WalletEarningAdapter
import com.talktomii.ui.mywallet.models.WalletEarningItemModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EarningFragment : DaggerFragment(R.layout.fragment_earning){

    private lateinit var binding: FragmentEarningBinding

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEarningBinding.inflate(inflater, container, false)
        recyclerview = binding.rvEarningWallet
        progress = binding.displayEarningsProgress
        progress!!.visibility  = View.VISIBLE
        recyclerview!!.visibility = View.GONE
        dataModel.getEarnings()
        return binding.root
    }
    companion object{
        var recyclerview : RecyclerView ?= null
        var progress : ProgressBar ?= null
    }
}