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
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.EditInterestFragmentBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.ui.editpersonalinfo.EditPersonalInfoVM
import com.talktomii.ui.home.profile.editinterest.AdapterEditInterest
import com.talktomii.ui.home.profile.editinterest.EditInterestVM
import com.talktomii.ui.home.profile.editinterest.GetItemsInterface
import com.talktomii.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import javax.inject.Inject

class EditInterestFragment : DaggerFragment(com.talktomii.R.layout.edit_interest_fragment),
    CommonInterface, GetItemsInterface {
    private lateinit var binding: EditInterestFragmentBinding

    @Inject
    lateinit var viewModel: EditInterestVM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var editPersonalInfoVM: EditPersonalInfoVM

    @Inject
    lateinit var prefsManager: PrefsManager
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
        binding.txtBack.setOnClickListener {
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
        initadapters()
    }

    private fun initadapters() {
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        binding.rvTopics.layoutManager = layoutManager
        binding.rvTopics.adapter = AdapterEditInterest(requireContext())
    }


    private fun init() {
        viewModel.commonInterface = this
        viewModel.onGetItems = this
        viewModel.getAllInterest()
        if (arguments != null) {
            if (requireArguments().getInt("Which") == 1) {
                binding.txtEditInterest.text = getString(R.string.edit_interests)
            } else {
                binding.txtEditInterest.text = getString(R.string.view_interests)
            }
        }
    }

    override fun onFailure(message: String) {
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {
        ApisRespHandler.handleError(
            ApiUtils.handleError(
                code,
                errorBody!!.string()
            ), requireActivity(), prefsManager
        )
    }

    override fun onStarted() {
    }

    override fun onItems(list: ArrayList<Interest>) {
        for (item in list) {
            for (item2 in editPersonalInfoVM.userField.get()!!.interest) {
                item.isClicked = item._id == item2._id
            }
        }
        (binding.rvTopics.adapter as AdapterEditInterest).setItemList(list, 2)
    }

}

