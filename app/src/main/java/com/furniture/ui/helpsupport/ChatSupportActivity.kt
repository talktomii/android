package com.furniture.ui.helpsupport

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.furniture.R
import com.furniture.databinding.ActivityChatSupportBinding
import com.furniture.databinding.HelpSupportBinding
import dagger.android.support.DaggerAppCompatActivity


class ChatSupportActivity : DaggerAppCompatActivity(){

    private lateinit var binding: ActivityChatSupportBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.furniture.R.layout.activity_chat_support)

        binding.tvchatBack.setOnClickListener {
            finish()
        }
    }
}