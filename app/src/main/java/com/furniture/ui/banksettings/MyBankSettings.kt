package com.furniture.ui.banksettings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.furniture.R
import com.furniture.databinding.MyBankSettingsBinding
import com.furniture.ui.banksettings.activities.AddBankAccountActivity
import com.furniture.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MyBankSettings : DaggerFragment(R.layout.my_bank_settings) {

    private lateinit var binding: MyBankSettingsBinding
    private val viewModels by viewModels<MyBankSettingsVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: MyBankSettingsVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyBankSettingsBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        binding.addBankAccountCard.setOnClickListener {
            val intent = Intent(requireContext(),AddBankAccountActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}