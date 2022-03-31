package com.talktomii.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.talktomii.R
import com.talktomii.databinding.SettingsBinding
import com.talktomii.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class Settings : DaggerFragment(R.layout.settings){

    private lateinit var binding: SettingsBinding
    private val viewModels by viewModels<SettingsVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: SettingsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root
    }
}