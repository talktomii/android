package com.talktomii.ui.editpersonalinfo.time


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.talktomii.R
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.databinding.BottomsheetAddtimeperiodBinding
import com.talktomii.ui.editpersonalinfo.AddTimePeriodInterface
import com.talktomii.utlis.DateUtils
import com.talktomii.utlis.DateUtils.convertStringToCalender
import com.talktomii.utlis.DateUtils.getFormatedFullDate
import com.talktomii.utlis.DateUtils.setDateToTimeUTCToLocal
import com.talktomii.utlis.monthsarray
import com.talktomii.utlis.showMessage
import java.text.SimpleDateFormat
import java.util.*


class AddTimePeriodBottomSheetFragment(
    var addTimePeriodInterface: AddTimePeriodInterface,
    var availaibility: Availaibility?,
    var position: Int?
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetAddtimeperiodBinding

    var isMonday = false
    var isTuesday = false
    var isWednesday = false
    var isThursday = false
    var isFriday = false
    var isSaturday = false
    var isSunday = false
    var calenderToFormat: String? = null
    var calenderFromFormat: String? = null
    var calenderTo: Calendar? = null
    var calenderFrom: Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetAddtimeperiodBinding.inflate(LayoutInflater.from(context))

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivClose.setBackgroundResource(R.drawable.closesheeticon_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivClose.setBackgroundResource(R.drawable.close_sheet_icon)
            }
        }
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (availaibility == null) {
            binding.tvDate.text = SimpleDateFormat("MMM dd yyyy").format(Date())
            binding.btnAddTimePeriod.visibility = View.VISIBLE
            binding.tvLabelAddTimePeriod.text = getString(R.string.add_time_period)
        } else {
            setdata()
            binding.btnAddTimePeriod.visibility = View.GONE
            binding.tvLabelAddTimePeriod.text = getString(R.string.view_time_period)
        }
        calenderTo = Calendar.getInstance()
        calenderFrom = Calendar.getInstance()
        setListeners()
    }

    private fun setdata() {

        binding.tvFrom.text = setDateToTimeUTCToLocal(availaibility!!.startTime)
        val calenderFrom = convertStringToCalender(availaibility!!.startTime)
        val calenderTo = convertStringToCalender(availaibility!!.endTime)
        calenderFromFormat = getFormatedFullDate(calenderFrom)
        calenderToFormat = getFormatedFullDate(calenderTo)
        binding.tvTo.text = setDateToTimeUTCToLocal(availaibility!!.endTime)
        for (item in availaibility!!.day) {
            if (item.equals("1")) {
                isMonday = true
            } else if (item.equals("2")) {
                isTuesday = true
            } else if (item.equals("3")) {
                isWednesday = true
            } else if (item.equals("4")) {
                isThursday = true
            } else if (item.equals("5")) {
                isFriday = true
            } else if (item.equals("6")) {
                isSaturday = true
            } else if (item.equals("7")) {
                isSunday = true
            }
        }
        setUpAllDays()
        if (availaibility!!.end != null) {
            if (availaibility!!.end == "Never") {
                binding.rbNever.isChecked = true
            } else {
                binding.rbOn.isChecked = true
                binding.tvDate.text = availaibility!!.end
            }
        } else {
            binding.rbNever.isChecked = true
        }

        if (availaibility == null) {
            binding.btnAddTimePeriod.text = "Add Time Period"

        } else {
            binding.btnAddTimePeriod.text = "Edit Time Period"

        }
    }

    private fun setUpAllDays() {
        if (!isMonday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvMonday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvMonday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvMonday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvMonday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isTuesday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvTuesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvTuesday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvTuesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvTuesday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isTuesday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvTuesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvTuesday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvTuesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvTuesday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isWednesday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvWednesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvWednesday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvWednesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvWednesday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isThursday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvThursday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvThursday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvThursday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvThursday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isFriday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvFriday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvFriday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvFriday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvFriday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isSaturday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSaturday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvSaturday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSaturday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvSaturday.setTextColor(requireContext().getColor(R.color.white))
            }
        }

        if (!isSunday) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSunday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvSunday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSunday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvSunday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setListeners() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.llDate.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            val c = Calendar.getInstance()
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val date =
                        monthsarray[monthOfYear] + " " + dayOfMonth.toString() + " " + year.toString()
                    binding.tvDate.text = date
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }

        binding.rbNever.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            binding.rbNever.isChecked = true
            binding.rbOn.isChecked = false

        }

        binding.llFrom.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
//            calenderFrom= Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
                calenderFrom!!.timeInMillis = System.currentTimeMillis()
                calenderFrom!!.set(Calendar.MINUTE, minute)
                calenderFrom!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                var AMPM = if (hourOfDay < 12) {
                    0
                } else {
                    1
                }
                calenderFrom!!.set(Calendar.AM_PM, AMPM)


                calenderFromFormat = getFormatedFullDate(calenderFrom!!)
                Log.e("Time 1", calenderFromFormat!!)
                binding.tvFrom.text = DateUtils.getTimeFormat(hourOfDay, minute)
            }, calenderFrom!!.get(Calendar.HOUR_OF_DAY), calenderFrom!!.get(Calendar.MINUTE), false)
            timePickerDialog.show()
        }

        binding.llTo.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            val timePickerDialog = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
                calenderTo!!.timeInMillis = System.currentTimeMillis()
                calenderTo!!.set(Calendar.MINUTE, minute)
                calenderTo!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                var AMPM = if (hourOfDay < 12) {
                    0
                } else {
                    1
                }
                calenderFrom!!.set(Calendar.AM_PM, AMPM)
                calenderToFormat = getFormatedFullDate(calenderTo!!)
                Log.e("Time 2", calenderToFormat!!)
                binding.tvTo.text = DateUtils.getTimeFormat(hourOfDay, minute)
            }, calenderTo!!.get(Calendar.HOUR_OF_DAY), calenderTo!!.get(Calendar.MINUTE), false)
            timePickerDialog.show()
        }

        binding.rbOn.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            binding.rbNever.isChecked = false
            binding.rbOn.isChecked = true
        }

        binding.tvMonday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupMonday()
        }

        binding.tvTuesday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupTuesday()
        }

        binding.tvWednesday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupWednesday()
        }

        binding.tvThursday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupThursday()
        }

        binding.tvFriday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupFriday()
        }

        binding.tvSaturday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupSaturday()
        }

        binding.tvSunday.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            setupSunday()
        }

        binding.btnAddTimePeriod.setOnClickListener {
            if (availaibility != null)
                return@setOnClickListener
            validation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    fun validation() {
        if (binding.tvFrom.text.isBlank()) {
            requireContext().showMessage(getString(R.string.empty_start_time))
            return
        }

        if (binding.tvTo.text.isBlank()) {
            requireContext().showMessage(getString(R.string.empty_end_time))
            return
        }

        if (!isMonday && !isTuesday && !isWednesday && !isThursday && !isFriday && !isSaturday && !isSunday) {
            requireContext().showMessage(getString(R.string.select_day))
            return
        }

        if (binding.rbNever.isChecked == false && binding.rbOn.isChecked == false) {
            requireContext().showMessage(getString(R.string.select_repeat_option))
            return
        }

        var days: ArrayList<String> = arrayListOf()
        if (isMonday) {
            days.add("1")
        }
        if (isTuesday) {
            days.add("2")
        }
        if (isWednesday) {
            days.add("3")
        }
        if (isThursday) {
            days.add("4")
        }
        if (isFriday) {
            days.add("5")
        }
        if (isSaturday) {
            days.add("6")
        }
        if (isSunday) {
            days.add("7")
        }
        var model = Availaibility(
            if (availaibility != null) availaibility!!._id else "",
            days,
            if (binding.rbNever.isChecked) "Never" else binding.tvDate.text.toString(),
            calenderToFormat.toString(),
            false,
            calenderFromFormat.toString()
        )

        if (availaibility == null) {
            addTimePeriodInterface.addTimePeriod(model, false, 0)
        } else {
            addTimePeriodInterface.addTimePeriod(model, true, position!!)
        }

        dismiss()
    }


    fun setupMonday() {
        if (isMonday) {
            isMonday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvMonday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvMonday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isMonday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvMonday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvMonday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setupTuesday() {
        if (isTuesday) {
            isTuesday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvTuesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvTuesday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isTuesday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvTuesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvTuesday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setupWednesday() {
        if (isWednesday) {
            isWednesday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvWednesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvWednesday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isWednesday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvWednesday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvWednesday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setupThursday() {
        if (isThursday) {
            isThursday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvThursday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvThursday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isThursday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvThursday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvThursday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setupFriday() {
        if (isFriday) {
            isFriday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvFriday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvFriday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isFriday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvFriday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvFriday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setupSaturday() {
        if (isSaturday) {
            isSaturday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSaturday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvSaturday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isSaturday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSaturday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvSaturday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

    fun setupSunday() {
        if (isSunday) {
            isSunday = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSunday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.white))
                binding.tvSunday.setTextColor(requireContext().getColor(R.color.black))
            }
        } else {
            isSunday = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tvSunday.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue_check))
                binding.tvSunday.setTextColor(requireContext().getColor(R.color.white))
            }
        }
    }

}