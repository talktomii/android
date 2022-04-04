package com.furniture.ui.drawer.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.data.model.drawer.bookmark.Payload
import com.furniture.data.model.drawer.bookmark.Service
import com.furniture.databinding.FragmentSettingBinding
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.drawer.BookMarkInterface
import com.furniture.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SettingFragment : DaggerFragment(), AdapterBookmark.onClickInteface, CommonInterface,
    BookMarkInterface {
    lateinit var binding: FragmentSettingBinding

    @Inject
    lateinit var viewModel: BookmarkViewModel

    private var adapter: AdapterBookmark? = null
    private var arrayList: ArrayList<Service> = arrayListOf()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        init()
    }

    private fun initAdapter() {
        adapter = AdapterBookmark(requireContext(), arrayList, this)
//        binding.rvPopular.adapter = adapter
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.onbookmarkinterface = this
        viewModel.getBookmarks("")
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String) {
        progressDialog.dismiss()
    }

    override fun onStarted() {
        progressDialog.show()
    }

    override fun onBookmarkAdmins(payload: Payload) {
        progressDialog.dismiss()
        adapter!!.setPopularList(payload.service)
    }

    override fun onClick(service: Service) {
        onCoverClicked(service)
    }

    private fun onCoverClicked(service: Service) {
        val bundle = Bundle()
        bundle.putSerializable("profileId", service.uid._id)
        findNavController().navigate(
            R.id.action_bookmarkFragment_to_influencerProfileFragment,
            bundle
        )
    }

}


