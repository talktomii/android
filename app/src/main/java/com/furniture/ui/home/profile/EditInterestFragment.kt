package com.furniture.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.furniture.R
import com.furniture.databinding.EditInterestFragmentBinding
import com.furniture.databinding.EditPersonalInfoFragmentBinding
import com.furniture.ui.editpersonalinfo.EditPersonalInfoVM
import com.furniture.ui.tellusmore.TellUsMoreVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EditInterestFragment : DaggerFragment(R.layout.edit_interest_fragment) {
    private lateinit var binding: EditInterestFragmentBinding

    private val viewModels by viewModels<TellUsMoreVM>()

    @Inject
    lateinit var viewModel: TellUsMoreVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditInterestFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root

        setListener()
    }

    private fun setListener() {
        binding.txtBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModels
        val recyclerview = binding.rvTopics
//        viewModels.isUser.set(args.isUser)

        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager
    }

}