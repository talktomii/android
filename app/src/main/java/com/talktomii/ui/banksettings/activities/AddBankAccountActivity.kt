package com.talktomii.ui.banksettings.activities

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.talktomii.R
import com.talktomii.databinding.ActivityAddBankAccountBinding
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.utlis.PrefsManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class AddBankAccountActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityAddBankAccountBinding

    @Inject
    lateinit var viewModel: MyCardsViewModel

    var selectedAccountType = ""
    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.talktomii.R.layout.activity_add_bank_account)
        getContext(this)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.backAddBankAccount.setImageResource(R.drawable.back_arrow)
                binding.bankGroup.setImageResource(R.drawable.bank_group_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.backAddBankAccount.setImageResource(R.drawable.back_arrow_light)
                binding.bankGroup.setImageResource(R.drawable.bank_group_light)
            }
        }
        layout = binding.banklayout
        binding.tvBankBack.setOnClickListener {
            finish()
        }
        progress = binding.addBankProgress

        val arrayStrings = ArrayList<String>()
        arrayStrings.add("Individual")
        arrayStrings.add("Company")
        Log.d("bank type : -- :", arrayStrings.toString())
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayStrings.toMutableList()
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bankspinner.setAdapter(adapter)
        binding.bankspinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedAccountType = adapter.getItem(p2)!!
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
//        binding.bankspinner.onItemSelectedListener { p0, p1, p2, p3 ->
//            selectedAccountType = adapter.getItem(p2)!!
//            if (intent.getStringExtra("bank") == "update") {
//                Log.d("spinner_data ::: ", intent.getStringExtra("type")!!)
//                if (arrayStrings.contains(intent.getStringExtra("type"))) {
//                    Log.d("spinner_data ::: ", intent.getStringExtra("type")!!)
//                    val spinnerPosition = adapter.getPosition(intent.getStringExtra("type"))
//                    binding.bankspinner.setSelection(spinnerPosition)
//                }
//            }
//        }

        binding.btnSaveBankAccount.setOnClickListener {
            if (binding.etaccountHolderName.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Enter Account Holder Name",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etRoutingNumber.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Enter Routing Number",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etRoutingNumber.text!!.length < 9) {
                val snackbar = Snackbar.make(
                    layout,
                    "Routing Number must have 9 digits",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etAccountNumber.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Enter Accounting Number",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etAccountNumber.text!!.length < 12) {
                val snackbar = Snackbar.make(
                    layout,
                    "Wrong Accounting Number",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etConfirmAccountNumber.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Re Enter Accounting Number",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etConfirmAccountNumber.text!!.length < 12) {
                val snackbar = Snackbar.make(
                    layout,
                    "Wrong Confirm Accounting Number",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.etAccountNumber.text!!.trim() != binding.etConfirmAccountNumber.text!!.trim()) {
                val snackbar = Snackbar.make(
                    layout,
                    "Accounting Number Not Matched",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else {
                val hashmap = HashMap<String, String>()
                hashmap["uid"] = prefsManager.getString(PrefsManager.PREF_API_ID, "")
                hashmap["routingNumber"] = binding.etRoutingNumber.text.toString()
                hashmap["accountNumber"] = binding.etConfirmAccountNumber.text.toString()
                hashmap["bankType"] = selectedAccountType
                hashmap["holderName"] = binding.etaccountHolderName.text.toString()
                progress.visibility = View.VISIBLE
                viewModel.addBank(hashmap)
            }

        }
        binding.btnCancelBankAccount.setOnClickListener {
            finish()
        }
    }

    companion object {
        lateinit var layout: ConstraintLayout
        lateinit var context: Context
        lateinit var progress: ProgressBar
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