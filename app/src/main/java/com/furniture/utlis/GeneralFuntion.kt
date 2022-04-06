package com.furniture.utlis

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.provider.Settings
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.androidisland.ezpermission.EzPermission
import com.bumptech.glide.Glide
import com.furniture.R
import com.furniture.data.model.UserData
import com.furniture.ui.loginSignUp.MainActivity
import com.furniture.utlis.listner.PermissionCallback
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.showMessage(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showMessage(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun EditText.isValidText(error: String):Boolean{
    if(this.text.toString().trim().isNotEmpty()){
        return true
    }
    this.error = error
    this.requestFocus()
    return false
}

fun ImageView.setImageFromFile(file: File?) {
    Glide.with(context).load(file).placeholder(R.color.grey_text).into(this@setImageFromFile)
}

fun EditText.isValidEmail(error: String):Boolean{
    if(isValidFormat(this.text.toString().trim())){
        return true
    }
    this.error  = error
    this.requestFocus()
    return false
}

fun EditText.isValidPass(error: String):Boolean{
    if(this.text.toString().trim().length>5){
        return true
    }
    this.error  = error
    this.requestFocus()
    return false
}

fun checkAccessFineLocationGranted(context: Context): Boolean {
    return ContextCompat
        .checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
}
fun showGPSNotEnabledDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("gps_gfg_enabled")
        .setMessage("required_for_this_app")
        .setCancelable(false)
        .setPositiveButton("Enable Now") { _, _ ->
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        .show()
}
//fun isLocationEnabled(context: Context): Boolean {
//    val gfgLocationManager: GfgLocationManager =
//        context.getSystemService(Context.LOCATION_SERVICE) as GfgLocationManager
//    return gfgLocationManager.isProviderEnabled(GfgLocationManager.GPS_PROVIDER)
//            || gfgLocationManager.isProviderEnabled(GfgLocationManager.NETWORK_PROVIDER)
//}

fun isValidFormat(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}


fun Context.locationPermission(permissionCallback: PermissionCallback) {
    EzPermission.with(this)
        .permissions(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        .request { granted, rat, permanentlyDenied ->
            when {
                granted.contains(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    permissionCallback.permissionGranted()
                }
                rat.contains(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    permissionCallback.permissionRejected()
                }
                permanentlyDenied.contains(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    PermissionUtils.showAppSettingsDialog(
                        this,
                        (R.string.location_permission_req)
                    )
                }
                permanentlyDenied.contains(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    PermissionUtils.showAppSettingsDialog(
                        this,
                        R.string.location_permission_req
                    )
                }
            }
        }
}


fun View.showSnackBar(msg: String) {
    try {
        val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        val textView =
            snackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.maxLines = 3
        snackBar.setAction(R.string.ok) { snackBar.dismiss() }
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        snackBar.setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        snackBar.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.showSnackBarWithoutOk(msg: String) {
    try {
        val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        val textView =
            snackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.maxLines = 3
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        snackBar.setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        snackBar.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun logoutUser(activity: Activity?, prefsManager: PrefsManager) {

    Log.d("logoutCalled", "clearData")

    val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancelAll()

    prefsManager.removeAll()

    activity.startActivity(
        Intent(activity, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    )

    activity.setResult(Activity.RESULT_CANCELED)
    ActivityCompat.finishAffinity(activity)
    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

fun FragmentManager.openFragmentWithAnimation(
    fragment: Fragment,
    containerId: Int,
    tag: String,
    addToBackStack: Boolean = true
) {
//    if (!isDestroyed && fragmentExist(tag)) {       //  Can not perform this action after onSaveInstanceState
    val transaction = beginTransaction()
    transaction.setCustomAnimations(
        R.anim.slide_in,
        R.anim.slide_out, R.anim.slide_left_in, R.anim.slide_out_right
    )
    transaction.add(containerId, fragment, tag)
    if (addToBackStack)
        transaction.addToBackStack(tag)
    transaction.commitAllowingStateLoss()       //onSaveInstanceState change to commit()
}

fun FragmentManager.openFragmentWithOutAnimation(
    fragment: Fragment,
    containerId: Int,
    tag: String,
    addToBackStack: Boolean = true
) {
//    if (!isDestroyed && fragmentExist(tag)) {       //  Can not perform this action after onSaveInstanceState
    val transaction = beginTransaction()

    transaction.add(containerId, fragment, tag)
    if (addToBackStack)
        transaction.addToBackStack(tag)
    transaction.commitAllowingStateLoss()       //onSaveInstanceState change to commit()
}

fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun ViewPager.autoScroll(interval: Long) {

    val handler = Handler()
    var scrollPosition = 0

    val runnable = object : Runnable {

        override fun run() {

            /**
             * Calculate "scroll position" with
             * adapter pages count and current
             * value of scrollPosition.
             */
            val count = adapter?.count ?: 0
            setCurrentItem(scrollPosition++ % count, true)

            handler.postDelayed(this, interval)
        }
    }

    handler.post(runnable)
}

fun TextView.makeLinks(color: Int, vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {

            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = color
                // toggle below value to enable/disable
                // the underline shown below the clickable text
//                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}


fun Context.cameraPermission(permissionCallback: PermissionCallback) {
    EzPermission.with(this).permissions(Manifest.permission.CAMERA)
        .request { granted, rat, permanentlyDenied ->
            when {
                granted.contains(Manifest.permission.CAMERA) -> {
                    permissionCallback.permissionGranted()
                }
                permanentlyDenied.contains(Manifest.permission.CAMERA) -> {
                    PermissionUtils.showAppSettingsDialog(
                        this,
                        (R.string.camera_permission_req)
                    )
                }
                permanentlyDenied.contains(Manifest.permission.CAMERA) -> {
                    PermissionUtils.showAppSettingsDialog(
                        this,
                        R.string.camera_permission_req
                    )
                }
            }
        }
}


fun Context.galleryclick(permissionCallback: PermissionCallback) {
    EzPermission.with(this)
        .permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        .request { granted, rat, permanentlyDenied ->
            if (granted.contains(Manifest.permission.READ_EXTERNAL_STORAGE) && granted.contains(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                permissionCallback.permissionGranted()
            } else if (permanentlyDenied.contains(Manifest.permission.READ_EXTERNAL_STORAGE) || granted.contains(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                PermissionUtils.showAppSettingsDialog(this, (R.string.storage_permission_req))
            } else if (permanentlyDenied.contains(Manifest.permission.READ_EXTERNAL_STORAGE) || granted.contains(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                PermissionUtils.showAppSettingsDialog(
                    this,
                    R.string.storage_permission_req
                )
            }
        }
}


fun getAccessToken(prefsManager: PrefsManager): String {
    return prefsManager.getString(PrefsManager.PREF_API_TOKEN, "")
}

fun getUser(prefsManager: PrefsManager): UserData? {
    return (prefsManager.getObject(PrefsManager.PREF_PROFILE, UserData::class.java))

}
fun getFormatFromDateUtc(date: Date, format: String): String? {
    val f = SimpleDateFormat(format, Locale.US)
    f.timeZone = TimeZone.getTimeZone("UTC")
    return f.format(date)
}

fun getDate(date: String): Calendar {
    val f = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    f.timeZone = TimeZone.getTimeZone("UTC")
    val cal = Calendar.getInstance();
    cal.setTime(f.parse(date))
    return cal
}


fun printDatesInMonth(year: Int, month: Int):ArrayList<String> {
    val list = ArrayList<String>()
    val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val cal = Calendar.getInstance()
    cal.clear()
    cal[year, month - 1] = 1
    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    for (i in 0 until daysInMonth) {
        list.add(fmt.format(cal.time))
        cal.add(Calendar.DAY_OF_MONTH, 1)
    }
    return list
}

fun convertFromUtcFormat(dateString: String?, toFormat: String): String {
    try {
        val from =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        from.setTimeZone(TimeZone.getTimeZone("UTC"))
        val date = from.parse(dateString ?: "") ?: Date()

        val tooo = SimpleDateFormat(toFormat, Locale.ENGLISH)
        tooo.setTimeZone(TimeZone.getDefault())
        val formattedDate = tooo.format(date)
        return formattedDate
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun convertFormat(dateString: String?, toFormat: String): String {
    try {
        val from =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
      //  from.setTimeZone(TimeZone.getTimeZone("UTC"))
        val date = from.parse(dateString ?: "") ?: Date()

        val tooo = SimpleDateFormat(toFormat, Locale.ENGLISH)
        tooo.setTimeZone(TimeZone.getDefault())
        val formattedDate = tooo.format(date)
        return formattedDate
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}



