package com.talktomii.ui.banksettings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.apis.WebService
import com.talktomii.databinding.MyBankSettingsBinding
import com.talktomii.ui.banksettings.activities.AddBankAccountActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MyBankSettings : DaggerFragment() {

    private lateinit var binding: MyBankSettingsBinding
    private val viewModels by viewModels<MyBankSettingsVM>()
    @Inject
    lateinit var viewModel: MyBankSettingsVM

    @Inject
    lateinit var dataModel: MyCardsViewModel
    lateinit var webService : WebService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = MyBankSettingsBinding.inflate(inflater, container, false)
        Companion.getContext(requireContext())
        recycleview = binding.rvDisplayBanks
        progress = binding.displayBankProgress
        progress.visibility = View.VISIBLE
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("RoleName",
            Context.MODE_PRIVATE
        )
        dataModel.getBank(sharedPreferences.getString("id","").toString())

        binding.addBankAccountCard.setOnClickListener {
            val intent = Intent(context,AddBankAccountActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object{
        lateinit var recycleview: RecyclerView
        lateinit var progress : ProgressBar
        lateinit var context: Context
        fun getContext(context: Context){
            this.context = context
        }
    }
}