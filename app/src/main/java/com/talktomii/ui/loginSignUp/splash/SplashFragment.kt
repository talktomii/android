package com.talktomii.ui.loginSignUp.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.utlis.LoginType
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
            var user = getUser(prefsManager)
            if (user!!.admin.role._id == LoginType.USER_ROLE)
                lifecycleScope.launchWhenResumed {
                    findNavController().navigate(R.id.homeFragment)
                }
            else
                lifecycleScope.launchWhenResumed {
                    findNavController().navigate(R.id.homeInfluencerFragment)
                }

//          view?.findNavController()?.navigate(R.id.homeFragment)
        } else {


            lifecycleScope.launchWhenResumed {
                findNavController().popBackStack()
                findNavController().navigate(R.id.loginFragment)
            }

        }


    }
}