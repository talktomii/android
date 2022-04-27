package com.talktomii.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.talktomii.R
import com.talktomii.databinding.SettingsBinding
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.ui.loginSignUp.MainVM
import com.talktomii.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class Settings : DaggerFragment(R.layout.settings) {

    private lateinit var binding: SettingsBinding
    private val viewModels by viewModels<SettingsVM>()

    private val viewModel: MainVM by viewModels()

    @Inject
    lateinit var prefsManager: PrefsManager

    companion object {
        lateinit var openNotification: LinearLayout
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsBinding.inflate(inflater, container, false)
        openNotification = binding.openNotifications
        openNotification.setOnClickListener {
            (activity as MainActivity).openNotificationFragment()
        }
        binding.privacyPolicyLayout.setOnClickListener {
            val intent = Intent(context, PrivacyAndPolicyActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}