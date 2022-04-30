package com.talktomii.ui.drawer.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.data.model.drawer.bookmark.Payload
import com.talktomii.data.model.drawer.bookmark.Service
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.FragmentBookmarkBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.drawer.BookMarkInterface
import com.talktomii.utlis.AboutMeDialog
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.common.CommonUtils
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import com.talktomii.utlis.isUser
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import javax.inject.Inject

class BookmarkFragment : DaggerFragment(), AdapterBookmark.onClickInteface, CommonInterface,
    BookMarkInterface {
    lateinit var binding: FragmentBookmarkBinding

    @Inject
    lateinit var prefsManager: PrefsManager

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
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        init()
    }

    private fun initAdapter() {
        adapter = AdapterBookmark(requireContext(), arrayList, this, isUser(prefsManager))
        binding.rvPopular.adapter = adapter
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.onbookmarkinterface = this
        viewModel.getBookmarks(getUser(prefsManager)!!.admin._id)
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

    override fun onBookmarkAdmins(payload: Payload) {
        progressDialog.dismiss()
        adapter!!.setPopularList(payload.service)
    }

    override fun onClick(service: Service, which: Int) {
        if (which == 1) {
            onCoverClicked(service)
        } else {
            if (service.ifid?.aboutYou != null) {
                val dialog = AboutMeDialog(service.ifid.aboutYou)
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

    private fun onCoverClicked(service: Service) {
        val bundle = Bundle()
        bundle.putSerializable("profileId", service.ifid._id)
        findNavController().navigate(
            R.id.action_bookmarkFragment_to_influencerProfileFragment,
            bundle
        )
    }

}


