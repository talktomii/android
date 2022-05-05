package com.talktomii

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.facebook.FacebookSdk
import com.talktomii.data.repos.UserRepository
import com.talktomii.di.DaggerAppComponent
import com.talktomii.utlis.PrefsManager
import com.google.android.libraries.places.api.Places
import com.zoho.salesiqembed.ZohoSalesIQ
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class ApplicationComponent : DaggerApplication() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(getApplicationContext());
        Places.initialize(getApplicationContext(), getString(R.string.google_api_key))
        ZohoSalesIQ.init(this, "LURMAt6PinFLr72Xk7wsURtmxqgmgCy5Ks%2BCI6uDcbfFWtb66uVlcBPCANC9LnQa", "4%2Fd2z2OovwMWRqqZhUbeyM4LvIxgMceQCJ%2FsoVO8Uoso1Tup3vOtznKYHedyfk4xLPDh9qQelK3n37pepPp1Wkm9REBsKgr2Hrz20Mx8hrx6cJmxWICjz3sA26sJXHHPXEcQDshTUiZW3QE2ZoQ0skr1MeUxasF8");
        ZohoSalesIQ.showLauncher(false)
        setsApplication(this)
    }



    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().create(this)

    companion object {

        /**
         * The service to be launched for the incoming call.
         */

        private var callServiceClass: Class<*>? = null

        private var isApplication: Application? = null

        fun getCallServiceClass(): Class<*>? {
            return callServiceClass
        }

        fun setCallServiceClass(callServiceClass: Class<*>) {
            Companion.callServiceClass = callServiceClass
        }

        fun setsApplication(sApplication: Application) {
            isApplication = sApplication
        }
    }
}