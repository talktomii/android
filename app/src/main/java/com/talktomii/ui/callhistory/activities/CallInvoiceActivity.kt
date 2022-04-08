package com.talktomii.ui.callhistory.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import com.talktomii.R
import com.talktomii.databinding.ActivityCallInvoiceBinding
import com.talktomii.ui.callhistory.adapters.CallHistoryAdapter
import dagger.android.support.DaggerAppCompatActivity
import io.github.lucasfsc.html2pdf.Html2Pdf
import java.io.File

class CallInvoiceActivity : DaggerAppCompatActivity() , Html2Pdf.OnCompleteConversion {

    lateinit var binding: ActivityCallInvoiceBinding
    var receipt_id : String = ""
    var filepath : File?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.WHITE;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_invoice)
        binding.tvBackCallInvoice.setOnClickListener {
            finish()
        }
        receipt_id = intent.getStringExtra("id")!!
        val date = intent.getStringExtra("date")
        val image_uri = intent.getStringExtra("image")
        val name = intent.getStringExtra("if_name")
        val amount =  "$" + intent.getIntExtra("amount",0).toString()
        val time = intent.getIntExtra("duration",0).toString() +  " Min"
        binding.callinvoiceId.text = receipt_id
        binding.callInvoiceName.text = name
        Picasso.with(this).load(image_uri).into(binding.callInvoiceimg)
        binding.callInvoicedate.text = date
        binding.callInvoiceAmount.text = amount

        binding.downloadCallInvoiceReceipt.setOnClickListener {
            if(receipt_id == ""){
                filepath =  File(Environment.getExternalStorageDirectory().toString() + "/PDF/callinvoice.pdf")
            }else{
                filepath =  File(Environment.getExternalStorageDirectory().toString() + "/PDF/callInvoice" + receipt_id + ".pdf")
            }
            val converter = Html2Pdf.Companion.Builder()
                .context(this)
                .html("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "  <head>\n" +
                        "    <meta charset=\"utf-8\" />\n" +
                        "    <title>Talk to me</title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <table\n" +
                        "      style=\"\n" +
                        "        border-collapse: collapse;\n" +
                        "        border-spacing: 0;\n" +
                        "        width: 100%;\n" +
                        "      \"\n" +
                        "    >\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><img src=\"file:///android_res/drawable/pdf_top_icon.png\"  alt=\"logo\"\n" +
                        "          style=\"width: 140px;\"/></td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\">\n" +
                        "          <b>Receipt : $receipt_id</b>\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Date</b</td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">$date</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Type</b</td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">Call</td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Provider</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">Taslktome inc.</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Duration</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">$time</td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Influencer</b></td>\n" +
                        "        <td style=\"text-align: right;\n" +
                        "         padding: 23px -1px;\n" +
                        "        display: flex;\n" +
                        "        align-items: center;\n" +
                        "        color: #167dd5;\n" +
                        "        justify-content: flex-end;\">\n" +
                        "        <img src=\"$image_uri\"  alt=\"logo\"\n" +
                        "          style=\"width: 50px;\n" +
                        "          height: 50px;\n" +
                        "          border-radius: 100%;\"/>&nbsp;&nbsp;&nbsp;&nbsp;$name&nbsp;&nbsp;</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Total</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">$amount</td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </body>\n" +
                        "</html>")
                .file(filepath!!)
                .build()
            //can be called with a callback to warn the user
            converter.convertToPdf(this)

            //or without a callback
            converter.convertToPdf()
            binding.opencallPDFProgress.visibility = View.VISIBLE
            Handler().postDelayed({
                openGeneratedPDF(filepath!!)
            },4000)
        }
    }
    private fun openGeneratedPDF(file : File) {
        binding.opencallPDFProgress.visibility = View.GONE
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val apkURI = FileProvider.getUriForFile(applicationContext, "$packageName.provider", file)
                intent.setDataAndType(apkURI, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/pdf")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "No Application available to view pdf",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onFailed() {

    }

    override fun onSuccess() {

    }
}