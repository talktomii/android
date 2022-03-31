package com.talktomii.ui.mywallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.FragmentRefillBinding
import com.talktomii.ui.mywallet.adapters.WalletRefillAdapter
import com.talktomii.ui.mywallet.models.WalletRefillItemModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RefillFragment : DaggerFragment(R.layout.fragment_refill){

    private lateinit var binding: FragmentRefillBinding

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRefillBinding.inflate(inflater, container, false)
        recyclerview = binding.rvRefillWallet
        progress = binding.displayRefillProgress
        progress!!.visibility  = View.VISIBLE
        recyclerview!!.visibility = View.GONE
        dataModel.getWallet()
        return binding.root
    }
    companion object{
        var recyclerview : RecyclerView ?= null
        var progress : ProgressBar ?= null
    }
}