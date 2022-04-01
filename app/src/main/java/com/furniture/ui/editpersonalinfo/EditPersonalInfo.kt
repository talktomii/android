package com.furniture.ui.editpersonalinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.furniture.R
import com.furniture.databinding.EditPersonalInfoFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EditPersonalInfo : DaggerFragment(R.layout.edit_personal_info_fragment) {
    private lateinit var binding: EditPersonalInfoFragmentBinding

    private val viewModels by viewModels<EditPersonalInfoVM>()

    @Inject
    lateinit var viewModel: EditPersonalInfoVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditPersonalInfoFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditPersonalInfoVM::class.java)
    }

}