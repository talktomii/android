package com.talktomii.ui.mycards.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.talktomii.R
import com.talktomii.databinding.ActivityPaymentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import android.os.Environment
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.*
import java.lang.Exception
import android.content.ActivityNotFoundException
import android.net.Uri
import android.util.Log
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfImportedPage
import android.view.View.MeasureSpec
import android.util.DisplayMetrics
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.print.PrintHelper
import com.itextpdf.text.PageSize


class PaymentDetailsActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityPaymentDetailsBinding

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
        binding.repeatPaymentLayout.setOnClickListener {
            val intent = Intent(this,RefillWalletActivity::class.java)
            intent.putExtra("repeatamount",binding.paymentAmount.text.toString().replace("$",""))
            startActivity(intent)
        }
        binding.refundPaymentLayout.setOnClickListener {
            val dialog = BottomSheetDialog(this)
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
            layoutToImage(binding.printdata)
            imageToPDF()
        }
    }

    var dirpath: String? = null
    fun layoutToImage(view: View) {

        view.setDrawingCacheEnabled(true)
        view.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight())

        view.buildDrawingCache(true)
        val bm: Bitmap = Bitmap.createBitmap(view.getDrawingCache())
        view.setDrawingCacheEnabled(false) // clear drawing cache
//        val bm: Bitmap = view.getDrawingCache()
        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/jpeg"
        val bytes = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val f = File(Environment.getExternalStorageDirectory().toString() + File.separator.toString() + "image.jpg")
        try {
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(FileNotFoundException::class)
    fun imageToPDF() {
        try {
            val document = Document(PageSize.A4, 36F, 36F, 36F, 36F)
            dirpath = Environment.getExternalStorageDirectory().toString()
            val writer : PdfWriter = PdfWriter.getInstance(
                document,
                FileOutputStream("$dirpath/NewPDF.pdf")
            ) //  Change pdf's name.

            document.open()
            val img: Image = Image.getInstance(
                Environment.getExternalStorageDirectory().toString() + File.separator + "image.jpg"
            )
            val scaler: Float = (document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth() * 100
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val documentWidth = (document.pageSize.width
                    - document.leftMargin() - document.rightMargin())
            val documentHeight = (document.pageSize.height
                    - document.topMargin() - document.bottomMargin())
            img.scaleToFit(documentWidth, documentHeight)

            val leftMargin: Float = document.pageSize.width - img.getScaledWidth()
            val lMargin = leftMargin / 2

            val topMargin: Float = document.pageSize.height - img.getScaledHeight()
            val tMargin = topMargin / 2

            img.setAbsolutePosition(lMargin, tMargin)
            document.add(img)
            document.close()
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show()
            val pdfReader = PdfReader("$dirpath/NewPDF.pdf")
            val image = Image.getInstance("image.jpg")
            val n: Int = pdfReader.getNumberOfPages()
            var page: PdfImportedPage
            // Traversing through all the pages
            // Traversing through all the pages
            for (i in 1..n) {
                page = writer.getImportedPage(pdfReader, i)
                val instance = Image.getInstance(page)
                //Save a specific page threshold for displaying in a scroll view inside your App
            }
            document.close()

        } catch (e: Exception) {
        }
    }

}