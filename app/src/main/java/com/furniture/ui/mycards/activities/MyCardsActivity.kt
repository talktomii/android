package com.furniture.ui.mycards.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.furniture.databinding.ActivityHomeBinding
import com.furniture.databinding.ActivityMyCardsBinding
import com.furniture.ui.home.HomeViewModel
import com.furniture.ui.mycards.data.MyCardsViewModel
import com.furniture.utlis.PrefsManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.text.Editable

import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.datepicker.MaterialDatePicker;
import android.annotation.SuppressLint

import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener

import android.R

import android.os.Build

import android.app.DatePickerDialog.OnDateSetListener

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.*
import android.widget.DatePicker.OnDateChangedListener
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


class MyCardsActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMyCardsBinding

    @Inject
    lateinit var viewModel: MyCardsViewModel

    lateinit var addCardButton: TextView
    lateinit var updateCardButton: TextView
    lateinit var cardNumber: TextInputEditText
    lateinit var cardHolderName: TextInputEditText
    lateinit var cardExpireDate: TextInputEditText
    lateinit var cardCVV: TextInputEditText

    lateinit var yYear: String
    lateinit var mMonth: String

    lateinit var inputDate: String

    companion object {
        lateinit var layout: RelativeLayout
        lateinit var progress : ProgressBar
        fun finish() {
            MyCardsActivity.finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.furniture.R.layout.activity_my_cards)

        cardHolderName = binding.etCardHolder
        addCardButton = binding.btnAddCard
        cardExpireDate = binding.etCardExpireDate
        cardNumber = binding.etCardNumber
        updateCardButton = binding.btnUpdateCard
        layout = binding.relativeLayout
        cardCVV = binding.etCVV
        progress = binding.addCardProgress


        val materialDateBuilder = MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("Select a Expire Date");
        val materialDatePicker = materialDateBuilder.build();
        materialDatePicker.addOnPositiveButtonClickListener(
            MaterialPickerOnPositiveButtonClickListener<Any?> {
                cardExpireDate.setText(materialDatePicker.headerText)
                yYear =
                    materialDatePicker.headerText.substring(materialDatePicker.headerText.length - 4)
                mMonth = materialDatePicker.headerText.substring(0, 2)
                cardExpireDate.setSelection(cardExpireDate.text!!.length)
                Log.d("dates is --- ", yYear + mMonth)
            })

        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val origin = s.toString().replace(" ".toRegex(), "")
                val formatStr: String = formatStrWithSpaces(origin)!!
                if (s.toString() != formatStr) {
                    editTextSetContentMemorizeSelection(cardNumber, formatStr)
                    if (before == 0 && count == 1 && formatStr[cardNumber.getSelectionEnd() - 1] == ' ') {
                        cardNumber.setSelection(cardNumber.getSelectionEnd() + 1)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })


        cardExpireDate.setOnClickListener {
            materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER");
        }

        addCardButton.setOnClickListener {
            if (cardNumber.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Please Add Card number",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (cardExpireDate.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Please Add Expire Date",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (cardCVV.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Please Add CVV",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else if (cardHolderName.text.toString() == "") {
                val snackbar = Snackbar.make(
                    layout,
                    "Please Add Card Holder Name",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else {
                val hashmap = HashMap<String, String>()
                hashmap["cardNumber"] = binding.etCardNumber.text.toString().replace(" ", "")
                hashmap["exp_month"] = mMonth
                hashmap["exp_year"] = yYear
                hashmap["cvv"] = binding.etCVV.text.toString()
                hashmap["holderName"] = binding.etCardHolder.text.toString()
                progress.visibility = View.VISIBLE
//                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                viewModel.addCard(hashmap)
            }
        }
    }

    fun formatStrWithSpaces(can: CharSequence): String? {
        val sb = StringBuffer()
        for (i in 0 until can.length) {
            if (i != 0 && i % 4 == 0) {
                sb.append(' ')
            }
            sb.append(can[i])
        }
        return sb.toString()
    }

    fun editTextSetContentMemorizeSelection(editText: EditText, charSequence: CharSequence) {
        var selectionStart = editText.selectionStart
        var selectionEnd = editText.selectionEnd
        editText.setText(charSequence.toString())
        if (selectionStart > charSequence.toString().length) {
            selectionStart = charSequence.toString().length
        }
        if (selectionStart < 0) {
            selectionStart = 0
        }
        if (selectionEnd > charSequence.toString().length) {
            selectionEnd = charSequence.toString().length
        }
        if (selectionEnd < 0) {
            selectionEnd = 0
        }
        editText.setSelection(selectionStart, selectionEnd)
    }


    class ExpirationDatePickerDialog(
        context: Context,
        callBack: OnDateSetListener?,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) :
        DatePickerDialog(
            context,
            if (Build.VERSION.SDK_INT >= 21) com.furniture.R.style.MyDialogTheme else 0,
            callBack,
            year,
            monthOfYear,
            dayOfMonth
        ), OnDateChangedListener {
        private fun init(context: Context) {
            setTitle("")
            datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.calendarViewShown = false
            val day: Int = context.getResources().getIdentifier("android:id/day", null, null)
            if (day != 0) {
                val dayPicker = datePicker.findViewById<View>(day)
                if (dayPicker != null) {
                    dayPicker.visibility = View.GONE
                }
            }
        }

        override fun onDateChanged(view: DatePicker, year: Int, month: Int, day: Int) {}

        init {
            init(context)
        }
    }
}
