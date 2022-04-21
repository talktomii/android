package com.talktomii.ui.home.profile

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.databinding.CallEndFragmentBinding
import com.talktomii.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CallEndFragment : DaggerFragment() {

    private lateinit var binding: CallEndFragmentBinding

    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        binding = CallEndFragmentBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivcloseBudges.setImageResource(R.drawable.closesheeticon_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivcloseBudges.setImageResource(R.drawable.close_sheet_icon)
            }
        }
        return binding.root

    }

    private fun setListener() {

        binding.txtClose.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLeaveTips.adapter = AdapterLeaveTip()
        binding.rvMyBudges.adapter = AdapterMyBudges()
        setListener()

    }
}
