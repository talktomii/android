package com.talktomii.ui.editpersonalinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.talktomii.R
import com.talktomii.databinding.EditPersonalInfoFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EditPersonalInfo : DaggerFragment(R.layout.edit_personal_info_fragment) {
    private lateinit var binding: EditPersonalInfoFragmentBinding

    private val viewModels by viewModels<EditPersonalInfoVM>()

    @Inject
    lateinit var viewModel: EditPersonalInfoVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditPersonalInfoFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModels
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditPersonalInfoVM::class.java)
    }

}