package com.talktomii

import android.app.Application
import androidx.lifecycle.ViewModelProvider
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
        Places.initialize(getApplicationContext(), getString(R.string.google_api_key))
        ZohoSalesIQ.init(this, "LURMAt6PinEUZWjOJ5lK0oSoQmZYs2i0d4uXNJoKL7igg4ATqcBrZBPCANC9LnQa", "4%2Fd2z2OovwMWRqqZhUbeyNYamBBD%2B2M8bt7CuDdhGSgRAWfmdGWcAbuxeE8XHt9aag9Q%2Fz9IXePIoJqtKLXwAn92kRJrIClz0OuUhO6zLqspOSOHmj0O4zL0fulvLCwtEfusNVLIPqNA41oqlACrwgegABBp2yaK");
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