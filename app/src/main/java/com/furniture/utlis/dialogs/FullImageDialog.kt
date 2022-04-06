package com.furniture.utlis.dialogs


import android.R
import android.app.Dialog

import android.view.LayoutInflater

import android.os.Bundle
import android.view.View

import android.view.ViewGroup

import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.furniture.databinding.ImageDialogBinding
import org.jetbrains.annotations.Nullable


/**
 * Created by Aamir Bashir on 09-02-2022.
 */
class FullImageDialog : DialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(requireContext())
        val binding: ImageDialogBinding =
            ImageDialogBinding.inflate(layoutInflater, container, false)
        val image: String = requireArguments().getString("IMAGE")!!
          Glide.with(requireContext()).load(image).into(binding.tvImageView)
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        return binding.getRoot()
    }

    companion object {
        fun newInstance(image: String): FullImageDialog? {
            val frag = FullImageDialog()
            val bundle = Bundle()
            bundle.putString("IMAGE", image)
            frag.arguments = bundle
            return frag
        }
    }
}