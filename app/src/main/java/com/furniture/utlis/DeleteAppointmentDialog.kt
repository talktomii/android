package com.furniture.utlis

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.databinding.CallDialogBinding
import com.furniture.databinding.DialogDeleteAppointmentBinding
import dagger.android.support.DaggerDialogFragment

class DeleteAppointmentDialog  : DaggerDialogFragment() {

    lateinit var binding: DialogDeleteAppointmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDeleteAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }

    private fun setListener() {
            binding.txtCancel.setOnClickListener {
                dismiss()
            }

    }

    companion object{
        val TAG="DeleteAppointmentDialog"
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}