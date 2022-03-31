package com.talktomii.ui.loginSignUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.talktomii.R
import com.talktomii.databinding.ActivityMainBinding
import com.talktomii.ui.helpsupport.HelpSupport
import com.talktomii.utlis.LocaleHelper
import com.talktomii.utlis.PrefsManager
import dagger.android.support.DaggerAppCompatActivity
import java.lang.ref.WeakReference
import javax.inject.Inject
import android.content.SharedPreferences
import android.widget.TextView
import com.talktomii.ui.mycards.data.MyCardsViewModel


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var navHostFragment: NavHostFragment? = null
    private val viewModel: MainVM by viewModels()

    companion object {
        lateinit var context: WeakReference<Context>
        var retrivedToken: String = ""
        var totalSideBarAmount : TextView ?= null
    }


    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = WeakReference(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // save login token here
        val token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyM2Q5ODMyZjUyMWFiMjhiOGY4MzczYSIsImRhdGUiOiIyMDIyLTAzLTMxVDA0OjM5OjA2Ljc4OFoiLCJlbnZpcm9ubWVudCI6ImRldmVsb3BtZW50IiwiZW1haWwiOiJmaW5hbEBnbWFpbC5jb20iLCJzY29wZSI6ImxvZ2luIiwidHlwZSI6InVzZXIiLCJpYXQiOjE2NDg3MDE1NDZ9.kBj2pBSZFZhymsEHpMLaJtPGTeT1nlXi77q0VzoZlQ0"
        val preferences: SharedPreferences = getSharedPreferences("MY_APP", MODE_PRIVATE)
        preferences.edit().putString("TOKEN", token).apply()
//        val preferences = context!!.getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        retrivedToken = preferences.getString("TOKEN", null)!!.trim()

        totalSideBarAmount = binding.textView9

        dataModel.getTotalAmount()

        binding.constWallet.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.myWalletFragment)
            binding.menuBottom.isVisible = true
            binding.btnMenu.isVisible = false
        }

        binding.viewModel = viewModel
        viewModel.navController = findNavController(R.id.nav_host_fragment)

        LocaleHelper.setLocale(this, prefsManager.getString(PrefsManager.LOCAL, "en"), prefsManager)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        binding.txtMyCards.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.cardFragment)
            binding.menuBottom.isVisible = true
        }

        binding.txtMyBankSettings.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.bankSettingsFragment)
            binding.menuBottom.isVisible = true
        }

        binding.txtSettings.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.settingsFragment)
            binding.menuBottom.isVisible = true
        }

        binding.txtCallHistory.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.callHistoryFragment)
            binding.menuBottom.isVisible = true
        }

        binding.txtHelpSupport.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            val intent = Intent(this, HelpSupport::class.java)
            startActivity(intent)
        }

        binding.menuBottom.setOnItemSelectedListener OnNavigationItemSelectedListener@{ item ->

            if (item.itemId != viewModel.navController.currentDestination!!.id) {
                when (item.itemId) {
                    R.id.nav_profile -> {
                        viewModel.navController.navigate(R.id.profileFragment)

                    }


                    R.id.nav_home -> {

                        viewModel.navController.navigate(R.id.homeFragment)
                    }


                    R.id.nav_search -> {
                        viewModel.navController.navigate(R.id.searchFragment)
                    }


                    R.id.nav_appointments -> {
                        viewModel.navController.navigate(R.id.appointmentsFragment)
                    }


                    R.id.nav_notifications -> {
                        viewModel.navController.navigate(R.id.notificationFragment)
                    }

                    R.id.nav_home -> {
                        viewModel.navController.navigate(R.id.homeFragment)
                    }


//                    R.id.nav_search ->{
//                        viewModel.navController.navigate(R.id.searchFragment)
//                    }
                }
            }

            true

        }

        binding.btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView)

            } else {
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
        }



        binding.btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView)

            } else {
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
        }

        viewModel.navController.addOnDestinationChangedListener { _, destination, _ ->

            if (
                destination.id == R.id.homeFragment ||
                destination.id == R.id.profileFragment ||
                destination.id == R.id.homeFragment ||
                destination.id == R.id.profileFragment ||
                destination.id == R.id.searchFragment ||
                destination.id == R.id.influencerProfileFragment ||
                destination.id == R.id.appointmentsFragment ||
                destination.id == R.id.notificationFragment

            ) {

                binding.menuBottom.selectedItemId = destination.id
                binding.menuBottom.isVisible = true
                binding.btnMenu.isVisible = true
                binding.btnMenu.isVisible = true

            } else {
                binding.menuBottom.isVisible = false
                binding.btnMenu.isVisible = false
            }
            binding.btnMenu.isVisible = destination.id == R.id.homeFragment
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}