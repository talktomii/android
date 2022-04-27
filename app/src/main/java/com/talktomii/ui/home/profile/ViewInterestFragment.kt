package com.talktomii.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.R
import com.talktomii.data.model.Interest
import com.talktomii.databinding.EditInterestFragmentBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.ui.editpersonalinfo.EditPersonalInfoVM
import com.talktomii.ui.home.profile.editinterest.AdapterEditInterest
import com.talktomii.ui.home.profile.editinterest.EditInterestVM
import com.talktomii.ui.home.profile.editinterest.GetItemsInterface
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewInterestFragment : DaggerFragment(com.talktomii.R.layout.edit_interest_fragment) {
    private lateinit var binding: EditInterestFragmentBinding

    @Inject
    lateinit var viewModel: EditInterestVM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var editPersonalInfoVM: EditPersonalInfoVM
    private var interestArrayList: ArrayList<Interest> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editPersonalInfoVM = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(EditPersonalInfoVM::class.java)
        binding = EditInterestFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }

    private fun setListener() {
        binding.tvBackIncomeDetail.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.txtSave.setOnClickListener {
            editPersonalInfoVM.updateInterestList((binding.rvTopics.adapter as AdapterEditInterest).getArrayList())
            findNavController().popBackStack(R.id.editPersonalInfo, false)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListener()
    }

    private fun initAdapters() {
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        binding.rvTopics.layoutManager = layoutManager
        binding.rvTopics.adapter = AdapterEditInterest(requireContext())
    }


    private fun init() {
        initAdapters()

        if (arguments != null) {

            interestArrayList.addAll( requireArguments().getSerializable("interest") as  ArrayList<Interest>)
            (binding.rvTopics.adapter as AdapterEditInterest).setItemList(interestArrayList, 2)
        }
    }


}

