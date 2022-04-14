package com.talktomii.ui.mywallet.activities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.talktomii.R
import com.talktomii.databinding.ActivityGetPaidBinding
import com.talktomii.ui.banksettings.activities.AddBankAccountActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class GetPaidActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityGetPaidBinding

    @Inject
    lateinit var viewModel: MyCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_paid)
        getContext(this)

        layout = binding.getPaidMainLayout
        amount = binding.getWalletAmount
        progressBar = binding.getPaidProgress
        total = binding.getPaidTotalAmountDetail
        filterTypes = binding.typesFilterwallet

        binding.tvgetpaidBack.setOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListOf("CITY BANK", "CITY BANK", "CITY BANK"))
        binding.typesFilterwallet.setAdapter(adapter)
        binding.typesFilterwallet.setText("CITY BANk")

        viewModel.getCurrentWallet()

        binding.btnGetPaid.setOnClickListener {
            if (binding.getPaidTotalAmountDetail.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Enter Amount",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }else{
                val hashmap = HashMap<String, String>()
                hashmap["amount"] = binding.getWalletAmount.text.toString()
                progressBar.visibility = View.VISIBLE
                viewModel.withDrawAmount(hashmap)
            }

        }
    }

    companion object{
        lateinit var layout: RelativeLayout
        lateinit var progressBar: ProgressBar
        lateinit var context: Context
        lateinit var amount : EditText
        lateinit var total : TextView
        var filterTypes : AutoCompleteTextView?= null
        fun getContext(context: Context) {
            this.context = context
        }

        fun finishFunction() {
            Handler().postDelayed({
                val activity = context as Activity
                activity.finish()
            }, 1000)
        }
    }
}