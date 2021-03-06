package com.talktomii.ui.mywallet.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.talktomii.databinding.ActivityRefillWalletBinding
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import com.talktomii.R
import com.talktomii.ui.mycards.activities.MyCardsActivity

class RefillWalletActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityRefillWalletBinding

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences = getSharedPreferences("RoleName",
            Context.MODE_PRIVATE
        )
        binding = DataBindingUtil.setContentView(this, com.talktomii.R.layout.activity_refill_wallet)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.backRefill.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.backRefill.setImageResource(R.drawable.back_arrow_light)
            }
        }
        getContext(this)
        etAmount = binding.addselectedAmount
        refillLayout = binding.refillMainLayout
        walletProgress = binding.addWalletProgress
        cardImg = binding.cardImage
        binding.selectAmount1.setOnClickListener {
            binding.addselectedAmount.setText("100")
            binding.addselectedAmount.setSelection(binding.addselectedAmount.text!!.length);
        }
        binding.selectAmount2.setOnClickListener {
            binding.addselectedAmount.setText("200")
            binding.addselectedAmount.setSelection(binding.addselectedAmount.text!!.length);
        }
        binding.selectAmount3.setOnClickListener {
            binding.addselectedAmount.setText("300")
            binding.addselectedAmount.setSelection(binding.addselectedAmount.text!!.length);
        }
        binding.tvrefillBack.setOnClickListener {
            finish()
        }
        binding.tvAddCard.setOnClickListener {
            getDetails = true
            val intent = Intent(context, MyCardsActivity::class.java)
            startActivity(intent)
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


        if(repeatAmount != "null"){
            binding.addselectedAmount.setText(repeatAmount.toString())
        }else{
            binding.addselectedAmount.setText("")
        }
        filterTypes = binding.cardspinner

        dataModel.getCardlistWallet(sharedPreferences.getString("id","").toString())

//        binding.typesFilter.setText("Select card")
    }

    companion object{
        var cardList : MutableList<String> ?= null
        var filterTypes : Spinner?= null
        var refillLayout : RelativeLayout ?= null
        var walletProgress : ProgressBar ?= null
        var etAmount : EditText ?= null
        lateinit var context : Context
        var getDetails : Boolean = false
        var repeatAmount : String = ""
        var cardImg : ImageView ?= null
        fun getContext(context: Context){
            this.context = context
        }
        fun finishFunction() {
            val activity = context as Activity
            activity.finish()
        }

    }
}