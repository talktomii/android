package com.talktomii.utlis

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.talktomii.utlis.DateFormate.CALENDER_DATE
import com.talktomii.utlis.DateFormate.CALENDER_SHORT_DATE
import com.talktomii.utlis.DateFormate.DATE_FORMAT_MMDDYYYY
import com.talktomii.utlis.DateFormate.DATE_FORMAT_WITH_DOT
import com.talktomii.utlis.DateFormate.DAY_MONTH_DATE_YEAR
import com.talktomii.utlis.DateFormate.FULL_DATE_FORMAT
import com.talktomii.utlis.DateFormate.FULL_DATE_FORMAT_WITH_DOT
import com.talktomii.utlis.DateFormate.LOCAL_DATE_FORMATE
import com.talktomii.utlis.DateFormate.TIME_FORMAT
import com.talktomii.utlis.DateFormate.WEEK_TIME_FORMAT
import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

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
                    DATE_FORMAT_MMDDYYYY,
                    DATE_FORMAT_MMDDYYYY,
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

    fun setDateToTime(startTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat(FULL_DATE_FORMAT)
            val outputFormat = SimpleDateFormat(TIME_FORMAT)
            val date = inputFormat.parse(startTime)
            val formattedDate = outputFormat.format(date)
            println(formattedDate) // prints 10-04-2018

            formattedDate
        } catch (e: java.lang.Exception) {
            ""
        }

    }

    fun setDateToTimeUTCToLocal(startTime: String): String {
        var dateToReturn: String = startTime

        val sdf = SimpleDateFormat(FULL_DATE_FORMAT)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        var gmt: Date? = null

        val sdfOutPutToSend = SimpleDateFormat(TIME_FORMAT)
        sdfOutPutToSend.timeZone = TimeZone.getDefault()

        try {
            gmt = sdf.parse(startTime)
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn

    }

    fun getTimeFormat(hr: Int, min: Int): String? {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = hr
        cal[Calendar.MINUTE] = min
        val formatter: Format
        formatter = SimpleDateFormat("h:mm a")
        return formatter.format(cal.time)
    }

    fun setDateToWeekDate(startTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat(FULL_DATE_FORMAT)
            val outputFormat = SimpleDateFormat(WEEK_TIME_FORMAT)
            val date = inputFormat.parse(startTime)
            val formattedDate = outputFormat.format(date)
            formattedDate
        } catch (e: java.lang.Exception) {
            ""
        }

    }

    fun getDateToShortDate(startTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat(FULL_DATE_FORMAT)
            val outputFormat = SimpleDateFormat(DATE_FORMAT_MMDDYYYY)
            val date = inputFormat.parse(startTime)
            val formattedDate = outputFormat.format(date)
            println(formattedDate) // prints 10-04-2018

            formattedDate
        } catch (e: java.lang.Exception) {
            ""
        }

    }

    fun getStringToDateWithDots(startTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat(FULL_DATE_FORMAT_WITH_DOT)
            val outputFormat = SimpleDateFormat(DATE_FORMAT_WITH_DOT)
            val date = inputFormat.parse(startTime)
            val formattedDate = outputFormat.format(date)
            println(formattedDate)

            formattedDate
        } catch (e: java.lang.Exception) {
            ""
        }

    }

    fun getFormatedFullDate(calendar: Calendar): String {
        try {
            val inputFormat = SimpleDateFormat(FULL_DATE_FORMAT)
            return inputFormat.format(calendar.time)

        } catch (e: java.lang.Exception) {
            return ""
        }
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

    fun DateAndTimeMerge(date: Date?, time: Date?): Date? {
        val aDate = Calendar.getInstance()
        aDate.time = date
        val aTime = Calendar.getInstance()
        aTime.time = time
        val aDateTime = Calendar.getInstance()
        aDateTime[Calendar.DAY_OF_MONTH] = aDate[Calendar.DAY_OF_MONTH]
        aDateTime[Calendar.MONTH] = aDate[Calendar.MONTH]
        aDateTime[Calendar.YEAR] = aDate[Calendar.YEAR]
        aDateTime[Calendar.HOUR] = aTime[Calendar.HOUR]
        aDateTime[Calendar.MINUTE] = aTime[Calendar.MINUTE]
        aDateTime[Calendar.SECOND] = aTime[Calendar.SECOND]
        return aDateTime.time
    }

    fun addMinutes(selectedStartTime: String, time: String): String {
        val df = SimpleDateFormat(FULL_DATE_FORMAT)
        val d = df.parse(selectedStartTime)
        val cal = Calendar.getInstance()
        cal.time = d
//        if (time.contains("min")) {
        val number: String = time.replace("[^0-9]", "")
        cal.add(Calendar.MINUTE, number.toInt())
//        }
        return df.format(cal.time)
    }

    fun convertStringToCalender(time: String): Calendar {
        val df = SimpleDateFormat(FULL_DATE_FORMAT)
        val d = df.parse(time)
        val cal = Calendar.getInstance()
        cal.time = d
        return cal
    }

    fun convertStringToCalenderWithOne(time: String): Calendar {
        val df = SimpleDateFormat(FULL_DATE_FORMAT)
        val d = df.parse(time)
        val cal = Calendar.getInstance()
        cal.time = d
        cal.add(Calendar.MONTH, 1)
        return cal
    }

    fun simpleDateToLocalToUTCDate(date: String): String {
        var dateToReturn: String? = null
        val sdf = SimpleDateFormat(FULL_DATE_FORMAT)
        sdf.timeZone = TimeZone.getDefault()
        var gmt: Date? = null

        val sdfOutPutToSend = SimpleDateFormat(FULL_DATE_FORMAT)
        sdfOutPutToSend.timeZone = TimeZone.getTimeZone("UTC")

        try {
            gmt = sdf.parse(date)
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn.toString()
    }

    fun simpleDateToUTCTOLocalDate(date: String): String {
        var dateToReturn: String? = null
        val sdf = SimpleDateFormat(FULL_DATE_FORMAT)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        var gmt: Date? = null

        val sdfOutPutToSend = SimpleDateFormat(FULL_DATE_FORMAT)
        sdfOutPutToSend.timeZone = TimeZone.getDefault()

        try {
            gmt = sdf.parse(date)
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn.toString()
    }

    fun shortDateToLocalToUTCDate(date: String): String {
        var dateToReturn: String? = null
        val sdf = SimpleDateFormat(CALENDER_SHORT_DATE)
        sdf.timeZone = TimeZone.getDefault()
        var gmt: Date? = null

        val sdfOutPutToSend = SimpleDateFormat(CALENDER_SHORT_DATE)
        sdfOutPutToSend.timeZone = TimeZone.getTimeZone("UTC")

        try {
            gmt = sdf.parse(date)
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun checkTimeIsBetween(startTime: String, endTime: String, checkTime: String): Boolean {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(FULL_DATE_FORMAT)
        val startLocalTime: LocalTime = LocalTime.parse(startTime, formatter)
        val endLocalTime: LocalTime = LocalTime.parse(endTime, formatter)
        val checkLocalTime: LocalTime = LocalTime.parse(checkTime, formatter)
        var isInBetween = false
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true
        }
        return isInBetween
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
    const val CALENDER_SHORT_DATE = "yyyy-MM-dd"
    const val DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy"
    const val DATE_FORMAT_WITH_DOT = "dd.MM.yyyy hh:mm aaa"
    const val TIME_FORMAT = "hh:mm a"
    const val WEEK_TIME_FORMAT = "EEE dd"
    const val FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val FULL_DATE_FORMAT_WITH_DOT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}