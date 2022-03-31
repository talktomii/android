package com.talktomii.utlis

import android.app.Application
import android.content.Context

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        context = getApplicationContext()
    }

    companion object {
        private var mInstance: AppController? = null
        private var context: Context? = null
        fun getContext(): Context? {
            return context
        }

        fun getmInstance(): AppController? {
            return mInstance
        }
    }
}