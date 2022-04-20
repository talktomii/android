package com.talktomii.ui.reportabuse

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.talktomii.R
import com.talktomii.databinding.ActivityReportAbuseBinding
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ReportAbuseActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityReportAbuseBinding

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_abuse)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.typesFilterContainer.setBackgroundResource(R.drawable.bg_paymentdetail_dark)
                binding.backReportIV.setImageResource(R.drawable.back_arrow)
                supportActionBar!!.hide()
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.typesFilterContainer.setBackgroundResource(R.drawable.bg_payment_detail)
                binding.backReportIV.setImageResource(R.drawable.back_arrow_light)
            }
        }
        getContext(this)
        binding.tvBackReportAbuse.setOnClickListener {
            finish()
        }
        filterTypes = binding.typesFilter
        layout = binding.reportabuseLayout
        dataModel.getType()

        binding.btnSendReport.setOnClickListener {
            Log.d("report data :  ",
                MyCardsViewModel.selectedType + "  -- " + binding.addReportAbuseMessage.text.toString()
                    .trim()
            )
            if (MyCardsViewModel.selectedType == "") {
                val snackbar = Snackbar.make(
                    ReportAbuseActivity.layout!!,
                    "Select Type",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (binding.addReportAbuseMessage.text.toString() == "") {
                val snackbar = Snackbar.make(
                    ReportAbuseActivity.layout!!,
                    "Add description",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else {
                val hashmap = HashMap<String, String>()
                hashmap["from"] = "622ee5910f837737301ce323"
                hashmap["to"] = "6239a38713ac0523946f5872"
                hashmap["description"] = binding.addReportAbuseMessage.text.toString().trim()
                hashmap["appointmentId"] = "623ea830621a4a71632e96dd"
                hashmap["type"] = "reportAbuse"
                hashmap["reportType"] = MyCardsViewModel.selectedType
                Log.d("---***----", hashmap.toString())
                dataModel.addFeedback(hashmap)
            }
        }

    }

    companion object {
        var filterTypes: AutoCompleteTextView? = null
        lateinit var context: Context
        lateinit var layout: RelativeLayout
        fun getContext(context: Context) {
            this.context = context
        }

        fun finishFunction() {
            Handler().postDelayed({
                val activity = context as Activity
                activity.finish()
            }, 3000)

        }
    }
}