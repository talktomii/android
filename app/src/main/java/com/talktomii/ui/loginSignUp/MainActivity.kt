package com.talktomii.ui.loginSignUp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var navHostFragment: NavHostFragment? = null
    private val viewModel: MainVM by viewModels()

    companion object {
        lateinit var context: WeakReference<Context>
        var retrivedToken: String = ""
    }


    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = WeakReference(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // save login token here
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyM2RhYzFiNmVlMWVmMjk4ODQyYjlhZCIsImRhdGUiOiIyMDIyLTAzLTMwVDA3OjAwOjQ2LjQyN1oiLCJlbnZpcm9ubWVudCI6ImRldmVsb3BtZW50IiwiZW1haWwiOiJqYW5pQGdtYWlsLmNvbSIsInNjb3BlIjoibG9naW4iLCJ0eXBlIjoidXNlciIsImlhdCI6MTY0ODYyMzY0Nn0.UdVV9VKSKGn25BI_ub2cRTmG90E5KCygpUWRi6ETmjY"
        val preferences: SharedPreferences = getSharedPreferences("MY_APP", MODE_PRIVATE)
        preferences.edit().putString("TOKEN", token).apply()
//        val preferences = context!!.getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        retrivedToken = preferences.getString("TOKEN", null)!!.trim()


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
                    R.id.nav_more -> {
                        binding.drawerLayout.openDrawer(binding.navigationView)
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

        viewModel.navController.addOnDestinationChangedListener { _, destination, _ ->

            if (
                destination.id == R.id.homeFragment ||
                destination.id == R.id.profileFragment ||
                destination.id == R.id.searchFragment ||
                destination.id == R.id.influencerProfileFragment ||
                destination.id == R.id.appointmentsFragment ||
                destination.id == R.id.notificationFragment ||
                destination.id == R.id.bookmarkFragment ||
                destination.id == R.id.settingsFragment ||
                destination.id == R.id.helpSupportFragment ||
                destination.id == R.id.editPersonalInfo
            ) {

                binding.menuBottom.selectedItemId = destination.id
                binding.menuBottom.isVisible = true
                binding.btnMenu.isVisible = true

            } else {
                binding.menuBottom.isVisible = false
                binding.btnMenu.isVisible = false
            }

            binding.btnMenu.isVisible = destination.id == R.id.homeFragment
        }


        //drawer
        binding.txtBookmarks.setOnClickListener {
            viewModel.navController.navigate(R.id.bookmarkFragment)
            binding.drawerLayout.closeDrawer(binding.navigationView)
        }
        binding.txtSettings.setOnClickListener {
            viewModel.navController.navigate(R.id.settingsFragment)
            binding.drawerLayout.closeDrawer(binding.navigationView)
        }
        if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
            binding.drawerLayout.closeDrawer(binding.navigationView)

        } else {
            binding.drawerLayout.openDrawer(binding.navigationView)
        }

        viewModel.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id == R.id.homeFragment ||
                destination.id == R.id.profileFragment
//                destination.id == R.id.searchFragment ||
//                destination.id == R.id.influencerProfileFragment

            ) {
                binding.menuBottom.selectedItemId = destination.id
                binding.menuBottom.isVisible = true
                binding.btnMenu.isVisible = true
            } else {
                binding.menuBottom.isVisible = false
                binding.btnMenu.isVisible = false
            }
        }
        binding.txtHelpSupport.setOnClickListener {
            viewModel.navController.navigate(R.id.helpSupportFragment)
            binding.drawerLayout.closeDrawer(binding.navigationView)
        }

        binding.txtProfile.setOnClickListener {
            viewModel.navController.navigate(R.id.editPersonalInfo)
            binding.drawerLayout.closeDrawer(binding.navigationView)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

}