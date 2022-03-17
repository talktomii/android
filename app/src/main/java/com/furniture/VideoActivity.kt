package com.furniture

import android.graphics.Camera
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.furniture.databinding.ActivityVideoBinding
import com.furniture.utlis.PrefsManager
import com.furniture.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.view.SurfaceHolder

import android.media.MediaRecorder
import android.widget.Button


class VideoActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityVideoBinding

    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView( this,R.layout.activity_video)

        }
    }


