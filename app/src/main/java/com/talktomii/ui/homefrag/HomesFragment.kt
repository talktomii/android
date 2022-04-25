package com.talktomii.ui.homefrag

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talktomii.R
import com.talktomii.data.model.admin.Admin
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.AppError
import com.talktomii.databinding.HomeFragmentBinding
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.HomeInterface
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.SocketManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

class HomesFragment : DaggerFragment(R.layout.home_fragment), HomeInterface, CommonInterface,
    AdapterPopular.onViewPopularClick, SocketManager.OnMessageReceiver {

    private lateinit var binding: HomeFragmentBinding
    private val viewModels by viewModels<HomeScreenViewModel>()
    private var adapterPopular: AdapterPopular? = null
    private var popularArrayList: ArrayList<Admin> = arrayListOf()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: HomeScreenViewModel
    private lateinit var progressDialog: ProgressDialog
    private var isShowMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        MainActivity.btnMenu.visibility = View.GONE
        val role: SharedPreferences = requireContext().getSharedPreferences("RoleName", MODE_PRIVATE)
        val roleName = role.getString("name","").toString()
        if(roleName == "user"){
            MainActivity.bookMark.visibility = View.VISIBLE
        }else{
            MainActivity.bookMark.visibility = View.GONE
        }
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivCross.setBackgroundResource(R.drawable.ic_cross_dark)
                MainActivity.btnMenu.visibility = View.GONE
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivCross.setBackgroundResource(R.drawable.ic_cross_light)
                MainActivity.btnMenu.visibility = View.GONE
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initAdapter() {
        adapterPopular = AdapterPopular(requireContext(), popularArrayList, this)
        binding.rvPopular.adapter = adapterPopular
    }

    private fun onCoverClicked(admin: Admin) {
        val bundle = Bundle()
        bundle.putSerializable("profileId", admin._id)
        findNavController().navigate(R.id.action_home_to_influencer_profile, bundle)
    }

    private fun init() {
        initAdapter()
        val admin = getUser(prefsManager)?.admin
        if (admin!!.fname != null) {
            binding.txtName.text = admin.fname
        } else {
            binding.txtName.text =  admin.name
        }
        progressDialog = ProgressDialog(requireActivity())
        viewModel.commonInterface = this
        viewModel.homeInterface = this
        if (arguments?.getString("ID") != null) {
            binding.constraintLayout.visibility = View.GONE
            binding.txtPopular.text = requireArguments().getString("name")!!.trim()
            binding.txtSeeAll.visibility = View.GONE
            binding.ivBackArrow.visibility = View.VISIBLE
            viewModel.getInfluence(arguments?.getString("ID")!!)
        } else {
            binding.constraintLayout.visibility = View.VISIBLE
            binding.txtPopular.text = "Popular"
            binding.txtSeeAll.visibility = View.VISIBLE
            binding.ivBackArrow.visibility = View.GONE
//            if (adapterPopular!!.getList().size > 0) {
//                adapterPopular!!.setPopularList(adapterPopular!!.getList())
//            } else {
                viewModel.getInfluence("")
//            }
        }
        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.txtSeeAll.setOnClickListener {
            handleShowMore()
        }
        handleShowMore()
    }

    private fun handleShowMore() {
        if (isShowMore) {
            adapterPopular!!.showMoreOrLess(isShowMore)
            isShowMore = false
            binding.txtSeeAll.text = "See less"
        } else {
            adapterPopular!!.showMoreOrLess(isShowMore)
            isShowMore = true
            binding.txtSeeAll.text = "See more"
        }
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {
        progressDialog.dismiss()
        val apiError: AppError = ApiUtils.getError(
            code,
            errorBody!!.string()
        )
        ApisRespHandler.handleError(apiError, requireActivity(), prefsManager)
    }

    override fun onStarted() {
        progressDialog.show()
    }

    override fun onViewPopularClick(admin: Admin) {
        onCoverClicked(admin)
    }

    override fun onHomeAdmins(payload: com.talktomii.data.model.admin.Payload) {
        progressDialog.dismiss()
        adapterPopular!!.setPopularList(payload.admin)
        var jsonObject = JSONObject()
        jsonObject.put("roomId", getUser(prefsManager)?.admin?._id)
        (requireActivity() as MainActivity).socketManager.joinApp(jsonObject, this)
    }

    override fun onMessageReceive(message: String, event: String) {

    }

}