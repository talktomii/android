package com.furniture.ui.editpersonalinfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.furniture.R
import com.furniture.databinding.BottomsheetAddpriceBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPriceBottomSheetFragment(var addpriceinterface: AddPriceInterface) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetAddpriceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomsheetAddpriceBinding.inflate(LayoutInflater.from(context))
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvMin.text = p1.toString() + " " + "min"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }


}