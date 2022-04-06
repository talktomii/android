package com.furniture.utlis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.furniture.R


/**
 * Created by RISHI PAUL SHARMA on 29/5/17.
 * CODE BREW LABS
 * rishipaul@code-brew@gmail.com
 */
class AlertDialogCommon {

    fun createOkCancelDialog(context: Context, @StringRes titleResourceId: Int, @StringRes messageResourceId: Int,
                             @StringRes positiveResourceId: Int, @StringRes negativeResourceId: Int, cancelable: Boolean,
                             listener: OnOkCancelDialogListener?): AlertDialog {
        val alertDialog = AlertDialog.Builder(context)
        if (titleResourceId != 0) {
            alertDialog.setTitle(titleResourceId)
        }
        if (titleResourceId != 0) {
            alertDialog.setMessage(messageResourceId)
        }
        alertDialog.setCancelable(cancelable)
        alertDialog.setPositiveButton(positiveResourceId
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
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat
                    .getColor(context, R.color.white))
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat
                    .getColor(context, R.color.white))
        }
        return dialog
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
