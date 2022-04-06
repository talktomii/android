package com.furniture.ui.banksettings.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.furniture.R
import com.furniture.databinding.ActivityAddBankAccountBinding
import dagger.android.support.DaggerAppCompatActivity

class AddBankAccountActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityAddBankAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.furniture.R.layout.activity_add_bank_account)

        binding.tvBankBack.setOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListOf("Select a bank account type", "Current", "Regular"))
        binding.typesFilter.setAdapter(adapter)
        binding.typesFilter.setText("Select a bank account type")


    }
}