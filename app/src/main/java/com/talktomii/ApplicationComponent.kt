package com.talktomii

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.talktomii.data.repos.UserRepository
import com.talktomii.di.DaggerAppComponent
import com.talktomii.utlis.PrefsManager
import com.google.android.libraries.places.api.Places
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