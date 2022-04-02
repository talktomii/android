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


class PaymentDetailsActivity : DaggerAppCompatActivity() {

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
    var receipt_id : String = "Receipt"


    private val filePath = Environment.getExternalStorageDirectory().toString() + "/PDF/"

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

        views = View(this)

        views = LayoutInflater.from(this).inflate(R.layout.test_layout, null)


        val vi = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = vi.inflate(R.layout.test_layout, null)

        val textView = v.findViewById<View>(R.id.pdf_total) as TextView
        textView.text = "your text"

        layout = views!!.findViewById(R.id.relLayout)
        v_date = views!!.findViewById(R.id.pdf_date)
        v_amount = views!!.findViewById(R.id.pdf_total)
        v_id = views!!.findViewById(R.id.pdf_receipt_no)

        (this as Activity).runOnUiThread(Runnable { (v_amount as TextView).text = "nnnnn" })

        v_id!!.text = intent.getStringExtra("id").toString()
        v_date!!.text = intent.getStringExtra("date").toString()
        v_amount!!.text = intent.getStringExtra("amount")!!.replace("-", "").toString()

        binding.downloadReceipt.setOnClickListener {
            Log.d("a", "a")
            val display: Display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width: Int = size.x
            val height: Int = size.y
            DesignPdf.with(this).addView(v)
                .setFilePath(filePath, receipt_id)
                .setMargins(0, 0, 10, 10)
                .setViewVisibilty(false)
                .setHeightnWidth(height, width)
                .create(object : Callback {
                    override fun onSuccess(file: File) {
                        Toast.makeText(
                            this@PaymentDetailsActivity,
                            "path: " + file.absolutePath,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("path", file.absolutePath)
                    }

                    override fun onError(e: Exception) {
                        e.printStackTrace()
                    }
                })
            Handler().postDelayed({
                openGeneratedPDF()
            }, 5000)

        }
    }

    private fun openGeneratedPDF() {
        dirpath = Environment.getExternalStorageDirectory().toString()
        val file = File("$dirpath/PDF/" + receipt_id + ".pdf")
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val apkURI =
                    FileProvider.getUriForFile(applicationContext, "$packageName.provider", file)
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

}