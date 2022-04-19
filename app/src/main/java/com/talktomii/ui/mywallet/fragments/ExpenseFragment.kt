package com.talktomii.ui.mywallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.FragmentExpenseBinding
import com.talktomii.databinding.FragmentRefillBinding
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ExpenseFragment : DaggerFragment(R.layout.fragment_expense){

    private lateinit var binding: FragmentExpenseBinding

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(inflater, container, false)
        recyclerview = binding.rvExpenseWallet
        progress = binding.displayExpenseProgress
        progress!!.visibility  = View.VISIBLE
        recyclerview!!.visibility = View.GONE
        dataModel.getExpenses()
        return  binding.root
    }

    companion object{
        var recyclerview : RecyclerView?= null
        var progress : ProgressBar?= null
    }

}