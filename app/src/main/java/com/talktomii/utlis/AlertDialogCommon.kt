package com.talktomii.utlis

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.talktomii.R


/**
 * Created by RISHI PAUL SHARMA on 29/5/17.
 * CODE BREW LABS
 * rishipaul@code-brew@gmail.com
 */
class AlertDialogCommon {

    fun createOkCancelDialog(
        context: Context, @StringRes titleResourceId: Int, @StringRes messageResourceId: Int,
        @StringRes positiveResourceId: Int, @StringRes negativeResourceId: Int, cancelable: Boolean,
        listener: OnOkCancelDialogListener?
    ): AlertDialog {
        val alertDialog = AlertDialog.Builder(context)
        if (titleResourceId != 0) {
            alertDialog.setTitle(titleResourceId)
        }
        if (titleResourceId != 0) {
            alertDialog.setMessage(messageResourceId)
        }
        alertDialog.setCancelable(cancelable)
        alertDialog.setPositiveButton(
            positiveResourceId
        ) { dialog, which ->
            listener?.onOkButtonClicked()
            dialog.dismiss()
        }
        if (negativeResourceId != 0) {
            alertDialog.setNegativeButton(negativeResourceId) { dialog, which ->
                listener?.onCancelButtonClicked()
                dialog.dismiss()
            }
        }
        val dialog = alertDialog.create()
        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(
                ContextCompat
                    .getColor(context, R.color.white)
            )
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(
                ContextCompat
                    .getColor(context, R.color.white)
            )
        }
        return dialog
    }


    fun createOkCancelDialogWithLayout(
        context: Context, titleResourceId: String,
        positiveResourceId: String, negativeResourceId: String, cancelable: Boolean,
        listener: OnOkCancelDialogListener?
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        val inflater: LayoutInflater = (context as Activity).layoutInflater

        val dialogView: View = inflater.inflate(R.layout.alert_label_editor, null)
        alertDialogBuilder.setView(dialogView)

        val tvAlertMessage = dialogView.findViewById<TextView>(R.id.tvAlertMessgae)
        val txtAlertCancel = dialogView.findViewById<TextView>(R.id.txtAlertCancel)
        val txtAlertDelete = dialogView.findViewById<TextView>(R.id.txtAlertDelete)
        tvAlertMessage.text = titleResourceId
        txtAlertCancel.text = positiveResourceId
        txtAlertDelete.text = titleResourceId



        alertDialogBuilder.setCancelable(cancelable)
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        txtAlertCancel.setOnClickListener {
            listener?.onCancelButtonClicked()
            alertDialog.dismiss()
        }

        txtAlertDelete.setOnClickListener {
            listener?.onOkButtonClicked()
            alertDialog.dismiss()
        }
    }


    interface OnOkCancelDialogListener {
        fun onOkButtonClicked()
        fun onCancelButtonClicked()
    }

    companion object {
        private var mInstance: AlertDialogCommon? = null

        val instance: AlertDialogCommon
            get() {
                if (null == mInstance) {
                    mInstance = AlertDialogCommon()
                }
                return mInstance as AlertDialogCommon
            }
    }
}
