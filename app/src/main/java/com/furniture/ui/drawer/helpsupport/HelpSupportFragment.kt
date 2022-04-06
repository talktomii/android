package com.furniture.ui.drawer.helpsupport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.databinding.FragmentHelpsupportBinding
import dagger.android.support.DaggerFragment

class HelpSupportFragment : DaggerFragment() {

    lateinit var binding: FragmentHelpsupportBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHelpsupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}


