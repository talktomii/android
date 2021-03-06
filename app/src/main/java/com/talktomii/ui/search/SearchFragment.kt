package com.talktomii.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.data.model.Interest
import com.talktomii.data.model.Payload
import com.talktomii.data.model.admin.Admin
import com.talktomii.databinding.SearchFragmentBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.OnAdminSearchInterface
import com.talktomii.interfaces.SearchInterface
import com.talktomii.interfaces.SearchItemClick
import com.talktomii.ui.homefrag.AdapterPopular
import com.talktomii.utlis.AboutMeDialog
import com.talktomii.utlis.common.CommonUtils
import com.talktomii.viewmodel.SearchViewModel
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import javax.inject.Inject

class SearchFragment : DaggerFragment(), SearchInterface, CommonInterface, SearchItemClick,
    OnAdminSearchInterface, AdapterPopular.onViewPopularClick {

    private lateinit var binding: SearchFragmentBinding

    @Inject
    lateinit var viewModel: SearchViewModel
    private var adapterCategories: AdapterCategories? = null
    private var adapterPopular: AdapterPopular? = null
    private var popularArrayList: ArrayList<Admin> = arrayListOf()

    private val search: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initAdapter() {
        adapterCategories = AdapterCategories(requireContext(), this)
        binding.rvCategories.adapter = adapterCategories

        adapterPopular = AdapterPopular(requireContext(), popularArrayList, this)
        binding.rvPopular.adapter = adapterPopular
    }

    private fun init() {
        viewModel.searchInterface = this
        viewModel.commonInterface = this
        viewModel.searchAdminsInterface = this
        viewModel.getAllInstruction(search)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etSearch.text.isEmpty()) {
                    binding.llCategories.visibility = View.VISIBLE
                    binding.llPopular.visibility = View.GONE
                } else {
                    binding.llPopular.visibility = View.VISIBLE
                    binding.llCategories.visibility = View.GONE
                    viewModel.getAdminsBySearch(binding.etSearch.text.toString())
                }
            }
        })

        binding.ivFilter.setOnClickListener {
            viewModel.getAllInstruction(binding.etSearch.text.toString())
        }

    }

    override fun onStarted() {

    }

    override fun onFailure(message: String) {
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {

    }

    override fun onSearchAllInstruction(data: Payload) {
        adapterCategories?.interestArrayList = arrayListOf()
        adapterCategories!!.setImagesList(data.interest)
    }

    override fun onViewSearchClick(interest: Interest) {
        view?.findNavController()
            ?.navigate(
                SearchFragmentDirections.actionSearchFragmentToHomeFragment(
                    interest._id,
                    interest.name
                )
            )
    }

    override fun onSearch(payload: com.talktomii.data.model.admin.Payload) {
        adapterPopular!!.setPopularList(payload.admin)
    }

    override fun onViewPopularClick(admin: Admin, which: Int) {
        if (which == 1) {
            onCoverClicked(admin)
        } else {
            if (admin.aboutYou != null) {
                val dialog = AboutMeDialog(admin.aboutYou)
                dialog.show(requireActivity().supportFragmentManager, AboutMeDialog.TAG)
            } else {
                context?.let { it1 ->
                    CommonUtils.showToastMessage(
                        it1,
                        getString(R.string.no_video_found)
                    )
                }
            }
        }
    }

    private fun onCoverClicked(admin: Admin) {
        val bundle = Bundle()
        bundle.putSerializable("profileId", admin._id)
        findNavController().navigate(
            R.id.action_searchFragment_to_influencerProfileFragment,
            bundle
        )
    }
}