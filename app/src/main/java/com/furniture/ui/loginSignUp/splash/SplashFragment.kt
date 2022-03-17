package com.furniture.ui.loginSignUp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.ui.home.HomeActivity
import com.furniture.utlis.PrefsManager
import com.furniture.utlis.getUser
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

            requireActivity().finishAffinity()
            val intent = Intent(context, HomeActivity::class.java)
            if (getUser(prefsManager)?.name?.isEmpty() == true)
                intent.putExtra("incompleteProfile", false)
            startActivity(intent)
        } else {

            findNavController().popBackStack()
            findNavController().navigate(R.id.loginFragment)

        }


    }
}