package com.furniture.ui.mywallet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.R
import com.furniture.databinding.FragmentCardBinding
import com.furniture.databinding.FragmentExpenseBinding
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