package com.talktomii.ui.home.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.databinding.FragmentHomeBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.utlis.*
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: HomeViewModel
    lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        MainActivity.btnMenu.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        const val TAG = "HomeFragment"
    }

}