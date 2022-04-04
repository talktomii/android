package com.talktomii.ui.loginSignUp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.ui.home.HomeActivity
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.getUser
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SplashFragment : DaggerFragment() {

    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            openFragments()
        }, 4000)

    }

    private fun openFragments() {
        if (prefsManager.getString(PrefsManager.PREF_API_TOKEN, "").isNotEmpty()) {

            findNavController().popBackStack()
            findNavController().navigate(R.id.homeFragment)
        } else {

            findNavController().popBackStack()
            findNavController().navigate(R.id.loginFragment)

        }


    }
}