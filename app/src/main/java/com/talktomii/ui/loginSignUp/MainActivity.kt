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
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.talktomii.databinding.SettingsBinding
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.ui.settings.Settings
import com.talktomii.utlis.SocketManager
import com.talktomii.utlis.logoutUser


class MainActivity : DaggerAppCompatActivity(), SocketManager.OnMessageReceiver {
    private lateinit var binding: ActivityMainBinding

    var navHostFragment: NavHostFragment? = null
    public var socketManager: SocketManager= SocketManager.getInstance()
    private val viewModel: MainVM by viewModels()

    companion object {
        lateinit var context: WeakReference<Context>
        var retrivedToken: String = ""
        var totalSideBarAmount : TextView ?= null
        lateinit var bottombar : BottomNavigationView
    }


    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socketManager.connect(this,prefsManager)
        context = WeakReference(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bottombar = binding.menuBottom

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

        binding.btnLogout.setOnClickListener {
            logoutUser(this,prefsManager)
        }
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

        binding.txtProfile.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.profileFragment)
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

        binding.txtBookmarks.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)
            viewModel.navController.navigate(R.id.bookmarkFragment)
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

//                    R.id.nav_home -> {
//                        viewModel.navController.navigate(R.id.homeFragment)
//                    }

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
                destination.id == R.id.profileFragment ||
                destination.id == R.id.searchFragment ||
                destination.id == R.id.influencerProfileFragment ||
                destination.id == R.id.appointmentsFragment ||
                destination.id == R.id.notificationFragment||
                destination.id == R.id.homeInfluencerFragment

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

    override fun onMessageReceive(message: String, event: String) {
        Log.e(event,message)
        runOnUiThread {
            Toast.makeText(this,event,Toast.LENGTH_SHORT).show()
        }
    }

    fun socketConnected() {
        socketManager.onCallRequest(this)
    }
}