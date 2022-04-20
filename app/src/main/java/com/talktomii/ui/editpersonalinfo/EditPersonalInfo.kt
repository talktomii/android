package com.talktomii.ui.editpersonalinfo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.talktomii.R
import com.talktomii.data.model.Admin
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.model.UpdateInfluence
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin.Price
import com.talktomii.data.model.admin.SendAvailaibility
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.EditPersonalInfoFragmentBinding
import com.talktomii.interfaces.AdminDetailInterface
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.UpdateProfileInterface
import com.talktomii.ui.editpersonalinfo.location.AddEditLocationBottomSheet
import com.talktomii.ui.editpersonalinfo.location.AddLocationInterface
import com.talktomii.ui.editpersonalinfo.time.AddTimePeriodBottomSheetFragment
import com.talktomii.ui.home.profile.AdapterAvailability
import com.talktomii.ui.home.profile.AdapterMySocialMedias
import com.talktomii.ui.home.profile.AdapterPrice
import com.talktomii.ui.home.profile.editinterest.AdapterEditInterest
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import com.talktomii.utlis.isUser
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class EditPersonalInfo : DaggerFragment(R.layout.edit_personal_info_fragment), AdminDetailInterface,
    CommonInterface, AdapterPrice.onViewEdiPriceClick, UpdateProfileInterface {
    private lateinit var binding: EditPersonalInfoFragmentBinding

    lateinit var profileImg_launcher: ActivityResultLauncher<Intent>
    lateinit var coverImg_launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: EditPersonalInfoVM

    private lateinit var progressDialog: ProgressDialog
    private var admin1: Admin1? = null

    private var fileProfile: File? = null
    private var fileCoverPhoto: File? = null
    private var isChangeProfile = false
    private var isChangeUserData = false

    @Inject
    lateinit var prefsManager: PrefsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(EditPersonalInfoVM::class.java)
        binding = EditPersonalInfoFragmentBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivBack.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivBack.setImageResource(R.drawable.back_arrow_light)
            }
        }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        profileImg_launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val filePath = com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
                    fileProfile = File(filePath)
                    Glide.with(requireContext()).load(fileUri).into(binding.imgDefault)
                }

            }

        coverImg_launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val filePath =
                        com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
                    fileCoverPhoto = File(filePath)
                    val inputStream: InputStream =
                        requireActivity().contentResolver.openInputStream(fileUri)!!
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
                    binding.layoutGrandiant.background = image
                }

            }


    }

    var availableAdapter: AdapterAvailability? = null
    private fun initAdapters() {
        binding.rvPrice.adapter = AdapterPrice(this)
        binding.rvInterest.adapter = context?.let { AdapterEditInterest(it) }
        availableAdapter = AdapterAvailability(object : AdapterAvailability.OnEditInterface {
            override fun onEdit(model: Availaibility, position: Int) {
                openTimePeriodBottomSheet(model, position)
            }
        })
        binding.rvAvailability.adapter = availableAdapter
        binding.rvSocialMedia.adapter = AdapterMySocialMedias(requireContext())
    }

    private fun setListeners() {

        binding.ivCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 99)
            } else {
                ImagePicker.with(this).createIntent { intent ->
                    profileImg_launcher.launch(intent)
                }
            }
        }

        binding.imgCam.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 99)
            } else {
                ImagePicker.with(this).createIntent { intent ->
                    coverImg_launcher.launch(intent)
                }
            }
        }

        binding.tvSave.setOnClickListener {

            if (isChangeProfile) {

                val map: HashMap<String, RequestBody> = HashMap()
                if (fileProfile != null) {
                    val body = fileProfile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    map["profilePhoto\"; filename=\"imageProfile.png\" "] = body
                }
                if (fileCoverPhoto != null) {
                    val body = fileCoverPhoto!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    map["coverPhoto\"; filename=\"imageCover.png\" "] = body
                }
                viewModel.updatePhoto(map, getUser(prefsManager)!!.admin._id)
            }



            if (!isUser(prefsManager)) {
                val updateInfluence: UpdateInfluence = UpdateInfluence()
//                updateInfluence.fname = binding.etFirstName.text.toString()
//                updateInfluence.lname = binding.etLastName.text.toString()
//                updateInfluence.userName = binding.etUsername.text.toString()
                val hashMap: HashMap<String, Any> = hashMapOf()
                hashMap["fname"] = binding.etFirstName.text.toString()
                hashMap["lname"] = binding.etLastName.text.toString()
                hashMap["userName"] = binding.etUsername.text.toString()

                val userData = viewModel.userField.get()
                var availaibility: ArrayList<SendAvailaibility> = arrayListOf()

                for (i in userData!!.availaibility) {
                    if (i.end == "Never" || i.end == null) {
                        i.end = ""
                    }
                    val availbility = SendAvailaibility()
                    availbility.day = i.day
                    availbility.end = i.end
                    availbility.endTime = i.endTime
                    availbility.startTime = i.startTime
                    availaibility.add(availbility)
                }
                hashMap["availaibility"] = availaibility
                hashMap["location"] = userData.location
                hashMap["price"] =
                    if (userData.price != null && userData.price.size > 0) userData.price[0].price.toInt() else 0
                hashMap["socialNetwork"] = userData.socialNetwork

                val interstArrayList: ArrayList<String> = arrayListOf()
                for (i in userData.interest) {
                    interstArrayList.add(i._id)
                }
                hashMap["interest"] = interstArrayList

//                updateInfluence.availaibility = userData!!.availaibility
//                updateInfluence.location = if (userData.location != null) userData.location else ""
//                updateInfluence.price =
//                    if (userData.price != null && userData.price.size > 0) userData.price[0].price.toInt() else 0
//                updateInfluence.socialNetwork =
//                    if (userData.socialNetwork != null && userData.socialNetwork.size > 0) userData.socialNetwork else arrayListOf()
//                updateInfluence.interest =
//                    if (userData.interest != null && userData.interest.size > 0) userData.interest else arrayListOf()
                viewModel.updateProfile(
                    hashMap,
                    getUser(prefsManager)!!.admin._id
                )
            } else {
//                val updateUser: UpdateUser = UpdateUser()
//
//
//                updateUser.lname = binding.etLastName.text.toString()
//                updateUser.userName = binding.etUsername.text.toString()
//                binding.etFirstName.text.toString().also { updateUser.fname = it }
//                val request = JSONObject(Gson().toJson(updateUser).trim())

                try {
                    val hashMap: HashMap<String, Any> = hashMapOf()
                    hashMap["fname"] = binding.etFirstName.text.toString()
                    hashMap["lname"] = binding.etLastName.text.toString()
                    hashMap["userName"] = binding.etUsername.text.toString()
                    viewModel.updateProfile(
                        hashMap,
                        getUser(prefsManager)!!.admin._id
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        binding.ivInterest.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("Which", 1)
            findNavController().navigate(R.id.action_editPersonalInfo_to_editInterestFragment)
        }

        binding.txtBudgesCount.setOnClickListener {
            findNavController().navigate(R.id.action_editPersonalInfo_to_myBudgesFragment)
        }

        binding.txtAddPrice.setOnClickListener {
            if ((binding.rvPrice.adapter as AdapterPrice).getItemListSize() == 0) {
                val bottomsheet = AddPriceBottomSheetFragment("", "", object : AddPriceInterface {
                    override fun addPrice(price: String) {
                        viewModel.userField.get()!!.price.add(Price("", price, ""))
                        updatePriceAdapter()
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
                            updatePriceAdapter()
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
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
        viewModel.adminDetailInterface = this
        viewModel.commonInterface = this
        viewModel.onUpdateProfileInterface = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setListeners()
        initAdapters()
        if (admin1 == null) {
            viewModel.getAdminById(getUser(prefsManager)!!.admin._id)
        } else {
            updateInterestAdapter()
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
//        if (admin1.coverPhoto != null) {
//            val url = URL(admin1.coverPhoto)
//            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
//            binding.layoutGrandiant.background = image
//        } else {
        binding.layoutGrandiant.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_gradient_profile)
//        }

        if (isUser(prefsManager)) {
            binding.constrainInfluancer.visibility = View.GONE
        } else {
            binding.constrainInfluancer.visibility = View.VISIBLE
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
            updateAvailabilityAdapter()
            (binding.rvSocialMedia.adapter as AdapterMySocialMedias).setItemList(viewModel.userField.get()!!.socialNetwork)

        }

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
                    updatePriceAdapter()
                }
            })
        bottomSheet.show(childFragmentManager, "addprice")
    }

    fun openTimePeriodBottomSheet(model: Availaibility?, position: Int?) {
        val bottomsheet = AddTimePeriodBottomSheetFragment(
            object : AddTimePeriodInterface {
                override fun addTimePeriod(model: Availaibility, isEdit: Boolean, position: Int) {
                    if (isEdit) {
                        viewModel.userField.get()!!.availaibility[position] = model
                    } else {
                        viewModel.userField.get()!!.availaibility.add(model)
                    }
                    updateAvailabilityAdapter()
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
        findNavController().popBackStack()


    }
}