package com.talktomii.ui.home.profile

import android.content.res.Configuration
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
import com.talktomii.data.model.Admin
import com.talktomii.data.model.Interest
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.EditInterestFragmentBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.UpdateProfileInterface
import com.talktomii.ui.editpersonalinfo.EditPersonalInfoVM
import com.talktomii.ui.home.profile.editinterest.AdapterEditInterest
import com.talktomii.ui.home.profile.editinterest.EditInterestVM
import com.talktomii.ui.home.profile.editinterest.GetItemsInterface
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import javax.inject.Inject

class EditInterestFragment : DaggerFragment(com.talktomii.R.layout.edit_interest_fragment),
    CommonInterface, GetItemsInterface, UpdateProfileInterface {
    private lateinit var binding: EditInterestFragmentBinding

    @Inject
    lateinit var viewModel: EditInterestVM

    @Inject
    lateinit var viewModel1: EditPersonalInfoVM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var editPersonalInfoVM: EditPersonalInfoVM

    @Inject
    lateinit var prefsManager: PrefsManager
    private lateinit var progressDialog: ProgressDialog

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
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.backArrow.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.backArrow.setImageResource(R.drawable.back_arrow_light)
            }
        }
        binding.vm = viewModel
        return binding.root
    }

    private fun setListener() {
        binding.headerLayout.setOnClickListener {
            requireActivity().onBackPressed()
        }

//        binding.txtSave.setOnClickListener {
//            editPersonalInfoVM.updateInterestList((binding.rvTopics.adapter as AdapterEditInterest).getArrayList())
//            findNavController().popBackStack(R.id.editPersonalInfo, false)
//
//        }
        binding.txtSave.setOnClickListener {
            val hashMap: HashMap<String, Any> = hashMapOf()
            val interstArrayList: ArrayList<String> = arrayListOf()
            var intersetSelectedList =
                (binding.rvTopics.adapter as AdapterEditInterest).getArrayList()
            for (i in intersetSelectedList) {
                if (i.isClicked)
                    interstArrayList.add(i._id)
            }
            hashMap["interest"] = interstArrayList

            viewModel1.updateProfile(
                hashMap,
                getUser(prefsManager)!!.admin._id
            )

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
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.onGetItems = this
        viewModel1.onUpdateProfileInterface = this
        viewModel1.commonInterface = this
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
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {
        progressDialog.dismiss()
        ApisRespHandler.handleError(
            ApiUtils.handleError(
                code,
                errorBody!!.string()
            ), requireActivity(), prefsManager
        )
    }

    override fun onStarted() {
        progressDialog.show()
    }

    override fun onItems(list: ArrayList<Interest>) {
        progressDialog.dismiss()
        for (item in list) {
            for (item2 in editPersonalInfoVM.userField.get()!!.interest) {
                if (item._id == item2._id) {
                    item.isClicked = true
                }
            }
        }
        (binding.rvTopics.adapter as AdapterEditInterest).setItemList(list, 2)
    }

    override fun onUpdateProfileDetails(admin1: Admin) {
        progressDialog.dismiss()
        findNavController().popBackStack()
    }

}

