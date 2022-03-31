package com.talktomii.ui.mywallet.activities

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.talktomii.databinding.ActivityRefillWalletBinding
import com.talktomii.ui.mycards.activities.MyCardsActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.app.Activity
import android.content.Intent
import com.talktomii.ui.mycards.data.getAllData


class RefillWalletActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityRefillWalletBinding

    @Inject
    lateinit var dataModel: MyCardsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.talktomii.R.layout.activity_refill_wallet)
        getContext(this)
        etAmount = binding.addselectedAmount
        refillLayout = binding.refillMainLayout
        walletProgress = binding.addWalletProgress
        binding.selectAmount1.setOnClickListener {
            binding.addselectedAmount.setText("100")
        }
        binding.selectAmount2.setOnClickListener {
            binding.addselectedAmount.setText("200")
        }
        binding.selectAmount3.setOnClickListener {
            binding.addselectedAmount.setText("300")
        }
        binding.tvrefillBack.setOnClickListener {
            finish()
        }
        repeatAmount = intent.getStringExtra("repeatamount").toString()
        binding.btnSubmitRefill.setOnClickListener {
            if(binding.addselectedAmount.text.toString().isNullOrEmpty()){
                val snackbar = Snackbar.make(
                    binding.refillMainLayout,
                    "Please Enter Amount",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }else if(MyCardsViewModel.selectedCardItem == ""){
                val snackbar = Snackbar.make(
                    binding.refillMainLayout,
                    "Please Select Card",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else{
                val hashmap = HashMap<String, String>()
                Log.d("paymentMethodId :: ",MyCardsViewModel.selectedCardItem)
                hashmap["amount"] = "-" + binding.addselectedAmount.text.toString().replace(" ", "")
                hashmap["paymentMethodId"] = MyCardsViewModel.selectedCardItem
                if(MyCardsViewModel.selectedCardItem != "") {
                    walletProgress!!.visibility = View.VISIBLE
                    dataModel.addWallet(hashmap)
                    if(repeatAmount != null){
                    }
                }
            }
        }


        if(repeatAmount != null){
            binding.addselectedAmount.setText(repeatAmount.toString())
        }else{
            binding.addselectedAmount.setText("")
        }
        filterTypes = binding.typesFilter
        dataModel.getCardlistWallet()

//        binding.typesFilter.setText("Select card")
    }

    companion object{
        var cardList : MutableList<String> ?= null
        var filterTypes : AutoCompleteTextView?= null
        var refillLayout : RelativeLayout ?= null
        var walletProgress : ProgressBar ?= null
        var etAmount : EditText ?= null
        lateinit var context : Context
        var repeatAmount : String = ""
        fun getContext(context: Context){
            this.context = context
        }
        fun finishFunction() {
            val activity = context as Activity
            activity.finish()
        }

    }
}