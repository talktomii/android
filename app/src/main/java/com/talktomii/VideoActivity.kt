package com.talktomii

import android.os.Bundle
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class VideoActivity : DaggerAppCompatActivity() {

//    lateinit var binding: ActivityVideoBinding

    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView( this,R.layout.activity_video)

        }
    }


