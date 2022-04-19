package com.talktomii.utlis

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.databinding.TermsAndConditionsDialogBinding
import com.talktomii.ui.loginSignUp.signup.SignUpFragment
import dagger.android.support.DaggerDialogFragment

class BackToHomeDialog(var onBtnClick: SignUpFragment) : DaggerDialogFragment() {
    lateinit var binding: TermsAndConditionsDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TermsAndConditionsDialogBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivImage.setImageResource(R.drawable.closesheeticon_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivImage.setImageResource(R.drawable.ic_cross_second)
            }
        }
        return binding.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }

    private fun setListener() {
        binding.ivImage.setOnClickListener {
            dismiss()
        }
    }

    interface OnBtnClick {
        fun onBackToHomeClick()
    }

    companion object{
        val TAG="BackToHomeDialog"
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