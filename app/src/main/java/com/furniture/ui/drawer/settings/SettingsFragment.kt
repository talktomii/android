package com.furniture.ui.drawer.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.databinding.FragmentSettingsBinding
import dagger.android.support.DaggerFragment

class SettingsFragment : DaggerFragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setlistener()
    }

    private fun setlistener() {
        binding.rlAccount.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_editPersonalInfo)
        }

        binding.rlNotifications.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_notificationFragment)
        }
    }


}


