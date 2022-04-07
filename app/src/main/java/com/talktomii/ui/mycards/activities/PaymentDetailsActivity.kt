package com.talktomii.ui.mycards.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.talktomii.databinding.ActivityPaymentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import dagger.android.support.DaggerAppCompatActivity
import android.os.Environment
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import com.itextpdf.text.pdf.PdfWriter
import java.io.*
import java.lang.Exception
import android.view.View.MeasureSpec
import android.util.DisplayMetrics
import com.talktomii.R
import android.widget.LinearLayout
import android.content.ActivityNotFoundException
import android.graphics.Point
import android.net.Uri
import androidx.core.content.FileProvider
import android.os.Build
import android.os.Handler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.widget.TextView
import com.itextpdf.text.*
import pdf.app.library.Callback
import pdf.app.library.DesignPdf
import android.view.ViewGroup
import android.content.Context
import io.github.lucasfsc.html2pdf.Html2Pdf

class PaymentDetailsActivity : DaggerAppCompatActivity(), Html2Pdf.OnCompleteConversion {

    lateinit var binding: ActivityPaymentDetailsBinding

    private var llScroll: LinearLayout? = null
    private var bitmap: Bitmap? = null

    private val mBottomSheetBehavior: BottomSheetBehavior<*>? = null
    var dirpath: String? = null

    var layout: LinearLayout? = null
    var linearLayout: LinearLayout? = null
    var views: View? = null

    var v_date: TextView? = null
    val v_type: TextView? = null
    var v_amount: TextView? = null
    var v_id: TextView? = null
    var receipt_id : String = ""
    var filepath : File ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_details)

        binding.headerLayout.setOnClickListener {
            finish()
        }
        binding.paymentReferenceid.text = intent.getStringExtra("id")
        binding.paymentDate.text = intent.getStringExtra("date")
        binding.paymentType.text = intent.getStringExtra("type")
        binding.paymentAmount.text = intent.getStringExtra("amount")!!.replace("-", "")

        val r_id = intent.getStringExtra("id")
        val date = intent.getStringExtra("date")
        val t_amount = intent.getStringExtra("amount")!!.replace("-", "")

        receipt_id = intent.getStringExtra("id")!!
        binding.repeatPaymentLayout.setOnClickListener {
            val intent = Intent(this, RefillWalletActivity::class.java)
            intent.putExtra("repeatamount", binding.paymentAmount.text.toString().replace("$", ""))
            startActivity(intent)
        }
        binding.refundPaymentLayout.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.MyTransparentBottomSheetDialogTheme)
            val view = layoutInflater.inflate(R.layout.bottomsheet_refund_payment, null)
            val btnClose = view.findViewById<ImageView>(R.id.btnCloseSheet)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }


        binding.downloadReceipt.setOnClickListener {
            if(receipt_id == ""){
                filepath =  File(Environment.getExternalStorageDirectory().toString() + "/PDF/receipt123.pdf")
            }else{
                filepath =  File(Environment.getExternalStorageDirectory().toString() + "/PDF/" + receipt_id + ".pdf")
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
                        "          <b>Receipt : $r_id</b>\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Date</b</td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">$date</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Payment Method</b</td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">VISA 50****1234</td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Provider</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">Taslktome inc.</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Address</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">1901,Surat</td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>VAT Number</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">123456789</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Product</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">Wallet Refill</td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Tax</b></td>\n" +
                        "         <td style=\"text-align: right; padding: 23px 7px;\">$0</td>\n" +
                        "      </tr>\n" +
                        "      <tr style=\"background-color: #f2f2f2\">\n" +
                        "        <td style=\"text-align: left; padding: 23px 7px;\"><b>Total</b></td>\n" +
                        "        <td style=\"text-align: right; padding: 23px 7px;\">$t_amount</td>\n" +
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
            binding.openPDFProgress.visibility = View.VISIBLE
            Handler().postDelayed({
                openGeneratedPDF(filepath!!)
            },3000)
        }
    }

    private fun openGeneratedPDF(file : File) {
        binding.openPDFProgress.visibility = View.GONE
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