package com.talktomii.utlis

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

object PermissionUtils {

    fun showAppSettingsDialog(context: Context, @StringRes messageResId: Int) {
        AlertDialog.Builder(context)
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(intent)
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, which -> dialog?.dismiss() }
            .setCancelable(false)
            .setMessage(messageResId)
            .show()
    }
}