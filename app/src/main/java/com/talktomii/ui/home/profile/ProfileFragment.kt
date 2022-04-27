package com.talktomii.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.model.Admin
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin.Price
import com.talktomii.data.model.admin.SendAvailaibility
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.FragmentProfileBinding
import com.talktomii.interfaces.*
import com.talktomii.ui.editpersonalinfo.AddPriceBottomSheetFragment
import com.talktomii.ui.editpersonalinfo.AddPriceInterface
import com.talktomii.ui.editpersonalinfo.AddTimePeriodInterface
import com.talktomii.ui.editpersonalinfo.EditPersonalInfoVM
import com.talktomii.ui.editpersonalinfo.location.AddEditLocationBottomSheet
import com.talktomii.ui.editpersonalinfo.location.AddLocationInterface
import com.talktomii.ui.editpersonalinfo.time.AddTimePeriodBottomSheetFragment
import com.talktomii.ui.home.profile.editinterest.AdapterEditInterest
import com.talktomii.ui.tellusmore.SocialNetwork
import com.talktomii.utlis.*
import com.talktomii.utlis.DateUtils.simpleDateToLocalToUTCDate
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import okhttp3.ResponseBody
import javax.inject.Inject

class ProfileFragment : DaggerFragment(), AdminDetailInterface,
    CommonInterface, AdapterPrice.onViewEdiPriceClick, UpdateProfileInterface,
    LinkAccountDialog.LinkListener, UpdateAvaibilityInterface, UpdatePhotoInterface {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: EditPersonalInfoVM

    private lateinit var progressDialog: ProgressDialog
    private var admin1: Admin1? = null

    private var fbLink: SocialNetwork = SocialNetwork(name = "facebook", link = "")
    private var twLink: SocialNetwork = SocialNetwork(name = "twitter", link = "")
    private var insLink: SocialNetwork = SocialNetwork(name = "instagram", link = "")
    private var tikLink: SocialNetwork = SocialNetwork(name = "tiktok", link = "")
    lateinit var profileImg_launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var prefsManager: PrefsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(EditPersonalInfoVM::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.TextEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editPersonalInfoDetails)
        }
        binding.txtBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        init()

    }

    var availableAdapter: AdapterAvailability? = null
    private fun initAdapters() {
        binding.rvPrice.adapter = AdapterPrice(this)
        binding.rvInterest.adapter = context?.let { AdapterEditInterest(it) }
        availableAdapter =
            AdapterAvailability(requireContext(), object : AdapterAvailability.OnEditInterface {
                override fun onEdit(model: Availaibility, position: Int, i: Int) {
                    openTimePeriodBottomSheet(model, position)
                }

                override fun onDelete(model: Availaibility, position: Int, which: Int) {
                    if (model._id.isNullOrEmpty()) {
                        onUpdateAvibility(position, model, which)
                    } else {
                        getUser(prefsManager)?.admin?.let {
                            viewModel.deleteAvailabilityTime(
                                position,
                                model._id,
                                it._id,
                                2
                            )
                        }

                    }
                }
            })
        binding.rvAvailability.adapter = availableAdapter
//        binding.rvSocialMedia.adapter = AdapterMySocialMedias(requireContext())
    }

    private fun setListeners() {
        binding.ivInterest.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("Which", 1)
            findNavController().navigate(R.id.action_profile_to_editInterestFragment)
        }

        binding.txtBudgesCount.setOnClickListener {
            var bundle: Bundle = Bundle()
            bundle.putSerializable("badges", admin1!!.badges)
            findNavController().navigate(R.id.action_profile_to_myBudgesFragment, bundle)
        }

        binding.txtAddPrice.setOnClickListener {
            if ((binding.rvPrice.adapter as AdapterPrice).getItemListSize() == 0) {
                val bottomsheet = AddPriceBottomSheetFragment("", "", object : AddPriceInterface {
                    override fun addPrice(price: String) {
                        viewModel.userField.get()!!.price.add(Price("", price, ""))
                        val hashMap: HashMap<String, Any> = hashMapOf()
                        var userData = viewModel.userField.get()!!
                        hashMap["price"] =
                            if (userData.price != null && userData.price.size > 0) userData.price[0].price.toInt() else 0
                        viewModel.updateProfile(
                            hashMap,
                            getUser(prefsManager)!!.admin._id
                        )
//                        updatePriceAdapter()
                    }
                })
                bottomsheet.show(childFragmentManager, "addprice")
            } else {
                val priceAdapter = (binding.rvPrice.adapter as AdapterPrice)
                val item = priceAdapter.getItemList()[0]
                val bottomSheet1 =
                    AddPriceBottomSheetFragment(item.price, item.time, object : AddPriceInterface {
                        override fun addPrice(price: String) {
                            viewModel.userField.get()!!.price[0] = Price(item._id, price, "")
//                            updatePriceAdapter()
                            val hashMap: HashMap<String, Any> = hashMapOf()
                            var userData = viewModel.userField.get()!!
                            hashMap["price"] =
                                if (userData.price != null && userData.price.size > 0) userData.price[0].price.toInt() else 0
                            viewModel.updateProfile(
                                hashMap,
                                getUser(prefsManager)!!.admin._id
                            )
                        }
                    })
                bottomSheet1.show(childFragmentManager, "addprice")
                Toast.makeText(
                    requireContext(),
                    getString(R.string.you_add_only_one_item),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.txtAddTime.setOnClickListener {
            openTimePeriodBottomSheet(null, 0)
        }

        binding.ivEditLocation.setOnClickListener {
            val bottomsheet = AddEditLocationBottomSheet(object : AddLocationInterface {
                override fun addPlace(place: String) {
                    viewModel.userField.get()!!.location = place
                    binding.tvLocation.text = place
                    val hashMap: HashMap<String, Any> = hashMapOf()
                    hashMap["location"] = place

                    viewModel.updateProfile(
                        hashMap,
                        getUser(prefsManager)!!.admin._id
                    )
                }
            })
            bottomsheet.show(childFragmentManager, "addlocation")
        }

        binding.txtItemCount.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("Which", 2)
            findNavController().navigate(
                R.id.action_editPersonalInfo_to_editInterestFragment,
                bundle
            )
        }

//        binding.rlFacebook.setOnClickListener {
//            val dialog = LinkAccountDialog("Facebook", this)
//            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
//        }

        binding.ivTwitter.setOnClickListener {
            val dialog = LinkAccountDialog("Twitter", twLink.link, this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }

        binding.ivInsta.setOnClickListener {
            val dialog = LinkAccountDialog("Instagram", insLink.link, this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }

        binding.ivTikTok.setOnClickListener {
            val dialog = LinkAccountDialog("Tiktok", tikLink.link, this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
        viewModel.adminDetailInterface = this
        viewModel.commonInterface = this
        viewModel.onUpdateProfileInterface = this
        viewModel.updatePhotoInterface = this
        binding.lifecycleOwner = this
        viewModel.updateAvailability = this
        binding.viewModel = viewModel
        setListeners()
        initAdapters()
//        if (admin1 == null) {
        viewModel.getAdminById(getUser(prefsManager)!!.admin._id)
//        } else {
//            updateInterestAdapter()
//        }


        binding.tvLabelAboutMe.setOnClickListener {
            if (admin1!!.aboutYou != null) {
                val dialog = AboutMeDialog(admin1!!.aboutYou)
                dialog.show(requireActivity().supportFragmentManager, AboutMeDialog.TAG)
            }
        }

        profileImg_launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

//                if (resultCode == Activity.RESULT_OK) {
//                    isChangeProfile = true
//                    //Image Uri will not be null for RESULT_OK
//                    val fileUri = data?.data!!
//                    val filePath = com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
//                    fileProfile = File(filePath)
//                    Glide.with(requireContext()).load(fileUri).into(binding.imgDefault)
//                }

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

    override fun onAdminDetails(admin1: Admin1) {
        progressDialog.dismiss()
        this.admin1 = admin1
        Glide.with(requireContext()).load(admin1.profilePhoto).placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user).into(binding.imgDefault)
        Glide.with(requireContext()).load(admin1.coverPhoto)
            .placeholder(R.drawable.bg_gradient_profile)
            .error(R.drawable.bg_gradient_profile).into(binding.layoutGrandiant)

        for (i in admin1.socialNetwork) {
            when (i.name.lowercase()) {
                "Facebook".lowercase() -> {
                    if (i.link != null)
                        fbLink.link = i.link
                }
                "Twitter".lowercase() -> {
                    if (i.link != null)
                        twLink.link = i.link
                }
                "Instagram".lowercase() -> {
                    if (i.link != null)
                        insLink.link = i.link
                }
                "Tiktok".lowercase() -> {
                    if (i.link != null)
                        tikLink.link = i.link
                }
            }
        }
//        if (admin1.coverPhoto != null) {
//            val url = URL(admin1.coverPhoto)
//            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
//            binding.layoutGrandiant.background = image
//        } else {
//        binding.layoutGrandiant.background =
//            ContextCompat.getDrawable(requireContext(), R.drawable.bg_gradient_profile)
//        }
        updatePriceAdapter()
        updateInterestAdapter()
        if (viewModel.userField.get()!!.interest.size > 0) {
            binding.rvPrice.visibility = View.VISIBLE
        } else {
            binding.rvPrice.visibility = View.GONE
        }
        (binding.rvInterest.adapter as AdapterEditInterest).setItemList(
            viewModel.userField.get()!!.interest,
            1
        )
        if (isUser(prefsManager)) {
            binding.constraintPrice.visibility = View.GONE
            binding.tvLabelFollowMe.visibility = View.GONE
            binding.constraintItems.visibility = View.GONE
            binding.constraintBadges.visibility = View.GONE
            binding.constarinAvaibility.visibility = View.GONE
        } else {
            binding.constraintPrice.visibility = View.VISIBLE
            binding.tvLabelFollowMe.visibility = View.VISIBLE
            binding.constraintItems.visibility = View.VISIBLE
            binding.constraintBadges.visibility = View.VISIBLE
            binding.constarinAvaibility.visibility = View.VISIBLE
        }
        updateAvailabilityAdapter()
//            (binding.rvSocialMedia.adapter as AdapterMySocialMedias).setItemList(viewModel.userField.get()!!.socialNetwork)


    }

    private fun updateInterestAdapter() {
        if (viewModel.userField.get()?.interest?.size ?: 0 > 3) {
            binding.txtItemCount.visibility = View.VISIBLE
            binding.txtItemCount.text = "+" + viewModel.userField.get()!!.interest.size.minus(3)
        } else {
            binding.txtItemCount.visibility = View.GONE
        }
        (binding.rvInterest.adapter as AdapterEditInterest).setItemList(
            viewModel.userField.get()!!.interest,
            1
        )
    }

    fun updatePriceAdapter() {
        if (viewModel.userField.get()!!.price.size > 0) {
            binding.rvPrice.visibility = View.VISIBLE
        } else {
            binding.rvPrice.visibility = View.GONE
        }
        (binding.rvPrice.adapter as AdapterPrice).setItemList(viewModel.userField.get()!!.price)
    }

    fun updateAvailabilityAdapter() {
        if (viewModel.userField.get()!!.availaibility.size > 0) {
            binding.rvAvailability.visibility = View.VISIBLE
        } else {
            binding.rvAvailability.visibility = View.GONE
        }
        availableAdapter?.setItemList(viewModel.userField.get()!!.availaibility)
    }

    override fun onViewItemPriceClick() {
        val priceAdapter = (binding.rvPrice.adapter as AdapterPrice)
        val item = priceAdapter.getItemList()[0]
        val bottomSheet =
            AddPriceBottomSheetFragment(item.price, item.time, object : AddPriceInterface {
                override fun addPrice(price: String) {

                    viewModel.userField.get()!!.price[0] = Price(item._id, price, "")
                    val hashMap: HashMap<String, Any> = hashMapOf()
                    var userData = viewModel.userField.get()!!
                    hashMap["price"] =
                        if (userData.price != null && userData.price.size > 0) userData.price[0].price.toInt() else 0
                    viewModel.updateProfile(
                        hashMap,
                        getUser(prefsManager)!!.admin._id
                    )

                }
            })
        bottomSheet.show(childFragmentManager, "addprice")
    }

    fun openTimePeriodBottomSheet(model: Availaibility?, position: Int?) {
        val bottomsheet = AddTimePeriodBottomSheetFragment(
            object : AddTimePeriodInterface {
                override fun addTimePeriod(model: Availaibility, isEdit: Boolean, position: Int) {
                    if (isEdit) {
                        addAPIAvaibility(model, false, position)
                    } else {
//                        viewModel.userField.get()!!.availaibility.add(model)
                        addAPIAvaibility(model, true, 0)
                    }
                    Log.e("Time 3", model.startTime)
                    Log.e("Time 4", model.endTime)
//                    updateAvailabilityAdapter()
                }
            },
            model, position
        )
        bottomsheet.show(childFragmentManager, "addtimeperiod")
    }

    override fun onUpdateProfileDetails(admin1: Admin) {
        progressDialog.dismiss()
        var registerModel: RegisterModel? = getUser(prefsManager)
        registerModel!!.admin = admin1
        prefsManager.save(PrefsManager.PREF_PROFILE, registerModel)
        var admin = viewModel.userField.get()
        admin!!.price = admin1.price
        admin.availaibility = admin1.availaibility
        viewModel.userField.set(admin)
        updatePriceAdapter()
        updateAvailabilityAdapter()
//        findNavController().popBackStack()
    }

    override fun onLinkClicked(type: String, value: String) {
        when (type) {
            "Facebook" -> {
                fbLink.link = value
            }
            "Twitter" -> {
                twLink.link = value
            }
            "Instagram" -> {
                insLink.link = value
            }
            "Tiktok" -> {
                tikLink.link = value
            }
        }
        val hashMap: HashMap<String, Any> = hashMapOf()
        hashMap["socialNetwork"] = arrayListOf(fbLink, twLink, insLink, tikLink)
        viewModel.updateProfile(
            hashMap,
            getUser(prefsManager)!!.admin._id
        )
    }

    override fun onUpdateAvibility(position: Int, model: Availaibility?, which: Int) {
        progressDialog.dismiss()
        if (which == 2) {
            deleteAvailabilityAdapter(position)
        } else {
            viewModel.userField.get()!!.availaibility[position] = model!!
            updateAvailabilityAdapter()
        }

    }

    private fun deleteAvailabilityAdapter(position: Int) {
        viewModel.userField.get()!!.availaibility.removeAt(position)
        availableAdapter?.deleteItem(position)
    }

    override fun onUpdatePhoto(admin: com.talktomii.data.photo.Admin) {
        progressDialog.dismiss()
    }

    private fun addAPIAvaibility(i: Availaibility, isNewDataAdd: Boolean, position: Int) {
        val hashMap: HashMap<String, Any> = hashMapOf()
        if (isNewDataAdd) {
            val availaibility: ArrayList<SendAvailaibility> = arrayListOf()

            if (i._id.isNullOrBlank()) {
                if (i.end == "Never" || i.end == null) {
                    i.end = ""
                }
                val availbility = SendAvailaibility()
                availbility.day = i.day
                availbility.end = i.end
                availbility.endTime = simpleDateToLocalToUTCDate(i.endTime)
                availbility.startTime = simpleDateToLocalToUTCDate(i.startTime)
                availaibility.add(availbility)
            }
            hashMap["availaibility"] = availaibility
        } else {
            val availaibility: ArrayList<Availaibility> = arrayListOf()
                if (i.end == "Never" || i.end == null) {
                    i.end = ""
                }
                val availbility = Availaibility()
                availbility.day = i.day
                availbility.end = i.end
                availbility._id = i._id
                availbility.endTime = simpleDateToLocalToUTCDate(i.endTime)
                availbility.startTime = simpleDateToLocalToUTCDate(i.startTime)
                availaibility.add(availbility)

            hashMap["availaibility"] = availaibility
//            viewModel.updateAvailabilityTime(updateHashMap, position, i, 1)

        }
        viewModel.updateProfile(
            hashMap,
            getUser(prefsManager)!!.admin._id
        )

    }

    companion object {
        const val TAG = "ProfileFragment"
    }

}