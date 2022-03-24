package com.furniture.ui.loginSignUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.furniture.R
import com.furniture.databinding.ActivityMainBinding
import com.furniture.ui.helpsupport.HelpSupport
import com.furniture.ui.home.homeFragment.HomeFragment
import com.furniture.ui.home.profile.ProfileFragment
import com.furniture.utlis.LocaleHelper
import com.furniture.utlis.PrefsManager
import dagger.android.support.DaggerAppCompatActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    var navHostFragment: NavHostFragment? = null
    private val viewModel: MainVM by viewModels()

    companion object {
        lateinit var context: WeakReference<Context>
    }


    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = WeakReference(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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
            val intent = Intent(this,HelpSupport::class.java)
            startActivity(intent)
        }

        binding.menuBottom.setOnItemSelectedListener OnNavigationItemSelectedListener@{ item->

            if (item.itemId != viewModel.navController.currentDestination!!.id){
                when(item.itemId){
                    R.id.nav_profile -> {
                        viewModel.navController.navigate(R.id.profileFragment)

                    }

                    R.id.nav_home ->{
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
            if(binding.drawerLayout.isDrawerOpen(binding.navigationView)){
                binding.drawerLayout.closeDrawer(binding.navigationView)

            }else{
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
        }


        viewModel.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id == R.id.homeFragment||
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

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}