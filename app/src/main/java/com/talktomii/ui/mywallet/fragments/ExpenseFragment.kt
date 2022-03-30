package com.talktomii.ui.mywallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.databinding.FragmentExpenseBinding
import dagger.android.support.DaggerFragment

class ExpenseFragment : DaggerFragment(R.layout.fragment_expense){

    private lateinit var binding: FragmentExpenseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense, container, false)
    }

}