package com.furniture.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.furniture.R
import com.furniture.databinding.ActivityHomeBinding
import com.furniture.ui.home.homeFragment.HomeFragment
import com.furniture.utlis.PrefsManager
import com.furniture.utlis.listner.PermissionCallback
import com.furniture.utlis.locationPermission
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.content.pm.PackageManager
import android.util.Base64


import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class HomeActivity : DaggerAppCompatActivity(), PermissionCallback {

    private lateinit var binding: ActivityHomeBinding

    lateinit var navViewController: NavController

    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
//        locationPermission(this)
        setListener()
//       Log.e("Key :: ", generateKeyHash())

            openFragment(HomeFragment(), HomeFragment.TAG)
    }

    private fun setListener() {
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        binding.btnMenu.setOnClickListener {
            if(binding.drawerLayout.isDrawerOpen(binding.navigationView)){
                binding.drawerLayout.closeDrawer(binding.navigationView)

            }else{
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
        }
    }

    private fun generateKeyHash():String {
        try {

            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )

            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.DEFAULT)
            }

        } catch (e: Exception) {
            Log.e("keyHash", e.message.toString())
        }

        return ""
    }
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            // Handle Bottom Navigation view clicks
            when (item.itemId) {
                R.id.nav_home -> {
                    openFragment(HomeFragment(), HomeFragment.TAG)

                    return@OnNavigationItemSelectedListener true
                }



            }
            false
        }

    private fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        supportFragmentManager.beginTransaction().add(R.id.flContainer, fragment, tag)
            .addToBackStack(tag).commit()
    }
    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            1 -> {
                finish()
            }
            else -> super.onBackPressed()
        }
    }

    override fun permissionGranted() {

    }

    override fun permissionRejected() {
        locationPermission(this)
    }

}

