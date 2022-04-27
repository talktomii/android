package com.talktomii.utlis

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.talktomii.R
import com.talktomii.databinding.DialogLinkAccountBinding
import dagger.android.support.DaggerDialogFragment

class LinkAccountDialog(var type: String, var link: String, var listener: LinkListener) :
    DaggerDialogFragment() {

    lateinit var binding: DialogLinkAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLinkAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtLinkYour.text =
            getString(R.string.link_your_facebook_account_n_to_talk_to_mii).replace("type", type)
        setListener()
    }

    private fun setListener() {
        binding.txtCancel.setOnClickListener {
            dismiss()
        }
        binding.etYourLink.setText(link)
        binding.txtLink.setOnClickListener {
            if (binding.etYourLink.text.toString().isNotEmpty()) {
                listener.onLinkClicked(type, binding.etYourLink.text.toString())
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Please Enter your link", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    companion object {
        val TAG = "LinkAccountDialog"
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    interface LinkListener {
        fun onLinkClicked(type: String, value: String)
    }
}