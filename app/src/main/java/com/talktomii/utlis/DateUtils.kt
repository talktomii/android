package com.talktomii.utlis

import android.app.Activity
import android.app.DatePickerDialog
import com.talktomii.utlis.DateFormate.CALENDER_DATE
import com.talktomii.utlis.DateFormate.DAY_MONTH_DATE_YEAR
import com.talktomii.utlis.DateFormate.LOCAL_DATE_FORMATE
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun openDatePicker(activity: Activity, listener: OnDateSelected, datee: Date?) {
        val c = Calendar.getInstance()
        var year: Int? = null
        var month: Int? = null
        var day: Int? = null

        if (datee == null) {
            year = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
        } else {
            val dob = Calendar.getInstance()
            dob.time = datee
            year = dob.get(Calendar.YEAR)
            month = dob.get(Calendar.MONTH)
            day = dob.get(Calendar.DAY_OF_MONTH)
        }

        val dpd = DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var selectedDate = "${monthOfYear.plus(1)}/$dayOfMonth/$year"
                // var selectedDate = "$dayOfMonth/${monthOfYear.plus(1)}/$year"
                selectedDate = dateFormatChange(
                    DateFormate.DATE_FORMAT_MMDDYYYY,
                    DateFormate.DATE_FORMAT_MMDDYYYY,
                    selectedDate
                )
                listener.onDateSelected(selectedDate)

            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = (System.currentTimeMillis() - 36000)
        dpd.show()
    }



    fun dateFormatChange(formatFrom: String, formatTo: String, value: String): String {
        if (value.isEmpty())
            return ""
        val originalFormat = SimpleDateFormat(formatFrom, Locale.ENGLISH)
        val targetFormat = SimpleDateFormat(formatTo, Locale.ENGLISH)
        val date = originalFormat.parse(value)
        val formattedDate = targetFormat.format(date)
        return formattedDate
    }

    fun getLocalToUTCDate(ourDate: String?, s: String): String? {
        var ourDate: String? = ourDate
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(ourDate)
            val dateFormatter = SimpleDateFormat("dd MMM,yyyy hh:mm") //this format changeable
            dateFormatter.timeZone = TimeZone.getDefault()
            ourDate = dateFormatter.format(value)

            //Log.d("ourDate", ourDate);
        } catch (e: Exception) {
            ourDate = "00-00-0000 00:00"
        }
        return ourDate
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat(LOCAL_DATE_FORMATE)
        return sdf.format(Date())
    }

    fun getDate(dateTime: CalendarDay): String {
        val date = "${dateTime.day}:${dateTime.month}:${dateTime.year}"
        return dateFormatChange(CALENDER_DATE, DAY_MONTH_DATE_YEAR, date)
    }


}
/*On Date selected listener*/
interface OnDateSelected {
    fun onDateSelected(date: String)
}
object DateFormate {
    const val DAY_MONTH_DATE_YEAR = "E,MMM,dd,yyyy"
    const val LOCAL_DATE_FORMATE = "dd/M/yyyy hh:mm:ss"
    const val CALENDER_DATE = "d:M:yyyy"
    const val DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy"
}