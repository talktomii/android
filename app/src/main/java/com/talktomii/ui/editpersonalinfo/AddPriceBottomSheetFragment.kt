package com.talktomii.ui.editpersonalinfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.talktomii.R
import com.talktomii.databinding.BottomsheetAddpriceBinding
import com.talktomii.utlis.showMessage

class AddPriceBottomSheetFragment(
    var price: String? = null,
    var time: String? = null,
    var addpriceinterface: AddPriceInterface
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetAddpriceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetAddpriceBinding.inflate(LayoutInflater.from(context))
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etPrice.setText(price.toString())

        val time = "15"
        if (!time.isNullOrEmpty() && time != "") {
            binding.seekbar.progress = time.toInt()
            binding.tvMin.text = "$time min"
        }
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvMin.text = p0?.progress.toString() + " " + "min"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.btnAddprice.setOnClickListener {
            if (binding.etPrice.text.isBlank()) {
                requireContext().showMessage(getString(R.string.empty_price))
                return@setOnClickListener
            }
            addpriceinterface.addPrice(binding.etPrice.text.toString())
            dismiss()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }


}