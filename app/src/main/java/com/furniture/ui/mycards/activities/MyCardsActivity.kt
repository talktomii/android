package com.furniture.ui.mycards.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.furniture.R
import com.furniture.databinding.ActivityHomeBinding
import com.furniture.databinding.ActivityMyCardsBinding
import com.furniture.utlis.PrefsManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MyCardsActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMyCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_cards)
    }
}