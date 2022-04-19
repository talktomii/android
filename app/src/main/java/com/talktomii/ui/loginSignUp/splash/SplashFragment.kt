package com.talktomii.ui.loginSignUp.splash

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
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
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
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


        getHashkey()
    }

    private fun openFragments() {
//        view?.findNavController()?.navigate(R.id.tellUsMore)
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

    fun getHashkey() {
        try {
            val info: PackageInfo = requireActivity().packageManager.getPackageInfo(
                requireActivity().packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("Base64:: ", Base64.encodeToString(md.digest(), Base64.NO_WRAP))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        // Log.d("Name not found", e.getMessage(), e)
        } catch (e: NoSuchAlgorithmException) {
        // Log.d("Error", e.getMessage(), e)
        }
}
}