package com.talktomii.data.network

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.talktomii.data.network.responseUtil.AppError
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.logoutUser
import com.talktomii.utlis.showMessage
import com.talktomii.R

object ApisRespHandler {

    private var alertDialog: AlertDialog.Builder? = null

    fun handleError(error: AppError?, activity: Activity, prefsManager: PrefsManager) {
        error ?: return
        when (error) {
            is AppError.ApiError -> {
                if (alertDialog == null)
                    errorMessage(activity, error.message)
            }

            is AppError.ApiFailure -> {
                if (alertDialog == null)
                    errorMessage(activity, error.message)
            }
            is AppError.ApiPermium -> {
                if (alertDialog == null)
                    errorMessage(activity, error.message)
            }
            is AppError.ApiAlertPremium -> {
                if (alertDialog == null)
                    errorMessage(activity, error.message)
            }

            is AppError.ApiUnauthorized -> {
                if (alertDialog == null)
                    sessionExpired(activity, error.message, prefsManager)
            }

            is AppError.ApiAccountBlock -> {
                if (alertDialog == null)
                    accountDeleted(activity, error.message, prefsManager)
            }

            is AppError.ApiAccountRuleChanged -> {
                if (alertDialog == null)
                    accountDeleted(activity, error.message, prefsManager)
            }


        }
    }

    private fun accountDeleted(activity: Activity, message: String?, prefsManager: PrefsManager) {
        try {
            alertDialog = AlertDialog.Builder(activity)

            alertDialog?.setCancelable(false)
            alertDialog?.setTitle(activity.getString(R.string.alert))
            alertDialog?.setMessage(message)
            alertDialog?.setPositiveButton(activity.getString(R.string.ok)) { _, _ ->
                logoutUser(activity, prefsManager)
                alertDialog = null
            }
            alertDialog?.show()

        } catch (ignored: Exception) {
        }
    }


    private fun sessionExpired(activity: Activity, message: String?, prefsManager: PrefsManager) {
        try {
            alertDialog = AlertDialog.Builder(activity, R.style.AlertDialogTheme)

            alertDialog?.setCancelable(false)

            alertDialog?.setTitle(activity.getString(R.string.alert))
            alertDialog?.setMessage(message)
            alertDialog?.setPositiveButton(activity.getString(R.string.login)) { _, _ ->
                logoutUser(activity, prefsManager)
                alertDialog = null
            }
            alertDialog?.show()
        } catch (ignored: Exception) {
        }
    }


    private fun errorMessage(activity: Activity, message: String?) {
        try {
            alertDialog = AlertDialog.Builder(activity, R.style.AlertDialogTheme)

            alertDialog?.setCancelable(false)
            alertDialog?.setTitle(activity.getString(R.string.alert))
            alertDialog?.setMessage(message)
            alertDialog?.setPositiveButton(activity.getString(R.string.ok)) { _, _ ->
                alertDialog = null
            }
            alertDialog?.show()

        } catch (ignored: Exception) {
            activity?.showMessage(message ?: "")
        }

    }

    fun successMessage(activity: Activity, message: String?) {
        try {
            alertDialog = AlertDialog.Builder(activity, R.style.AlertDialogTheme)
            alertDialog?.setCancelable(false)
            alertDialog?.setTitle(activity.getString(R.string.message))
            alertDialog?.setMessage(message)
            alertDialog?.setPositiveButton(activity.getString(R.string.ok)) { _, _ ->
                alertDialog = null
            }
            alertDialog?.show()

        } catch (ignored: Exception) {
        }

    }
}