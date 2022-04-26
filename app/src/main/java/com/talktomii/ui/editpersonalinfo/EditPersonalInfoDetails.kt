package com.talktomii.ui.editpersonalinfo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.EditPersonalInfoDetailFragmentBinding
import com.talktomii.interfaces.*
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.common.CommonUtils.Companion.showToastMessage
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.getUser
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject


class EditPersonalInfoDetails : DaggerFragment(R.layout.edit_personal_info_fragment),
    AdminDetailInterface,
    CommonInterface, UpdateProfileInterface,
    UpdatePhotoInterface, UpdateAboutYouVideo {
    private lateinit var binding: EditPersonalInfoDetailFragmentBinding

    lateinit var profileImg_launcher: ActivityResultLauncher<Intent>
    lateinit var gallerySelectRecord: ActivityResultLauncher<Intent>
    lateinit var videoRecord: ActivityResultLauncher<Intent>
    lateinit var coverImg_launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: EditPersonalInfoVM

    private lateinit var progressDialog: ProgressDialog
    private var admin1: Admin1? = null

    private var fileProfile: File? = null
    private var fileAboutYou: File? = null
    private var fileCoverPhoto: File? = null
    private var isChangeProfile = false
    private var isAddVideoRecord = false
    private var fileUri: Uri? = null

    @Inject
    lateinit var prefsManager: PrefsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditPersonalInfoDetailFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(EditPersonalInfoVM::class.java)
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



    }

    private fun setListeners() {

        binding.ivCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    99
                )
            } else {
                ImagePicker.with(this).createIntent { intent ->
                    profileImg_launcher.launch(intent)
                }
            }
        }

        binding.imgCam.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    99
                )
            } else {
                ImagePicker.with(this).createIntent { intent ->
                    coverImg_launcher.launch(intent)
                }
            }
        }

        binding.videoViewProfile.setOnClickListener {
            recordVideo()
        }

        binding.tvSave.setOnClickListener {

            if (isChangeProfile) {
                val map: HashMap<String, RequestBody> = HashMap()
                if (fileProfile != null) {
                    val body = fileProfile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    map["profilePhoto\"; filename=\"imageProfile.png\" "] = body
                }
                if (fileCoverPhoto != null) {
                    val body1 = fileCoverPhoto!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    map["coverPhoto\"; filename=\"coverPhoto.png\" "] = body1
                }
                viewModel.updatePhoto(map, getUser(prefsManager)!!.admin._id)
            }
            if (isAddVideoRecord){
                val map: HashMap<String, RequestBody> = HashMap()
                if (fileAboutYou != null) {
                    val body = fileAboutYou!!.asRequestBody("video/mp4".toMediaTypeOrNull())
                    map["aboutYou\"; filename=\"aboutYou.mp4\" "] = body
                }
                viewModel.updateAboutYou(map, getUser(prefsManager)!!.admin._id)
            }
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

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
        viewModel.adminDetailInterface = this
        viewModel.commonInterface = this
        viewModel.onUpdateProfileInterface = this
        viewModel.updatePhotoInterface = this
        binding.lifecycleOwner = this
        viewModel.onUpdateAboutYou = this
        binding.viewModel = viewModel
        setListeners()
        if (admin1 == null) {
            viewModel.getAdminById(getUser(prefsManager)!!.admin._id)
        }

        profileImg_launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    isChangeProfile = true
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val filePath = com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
                    fileProfile = File(filePath)
                    binding.videoViewProfile.setVideoURI(fileUri)
                }

            }

        coverImg_launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    isChangeProfile = true
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val filePath =
                        com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
                    fileCoverPhoto = File(filePath)
                    Glide.with(requireContext()).load(filePath).into(binding.layoutGrandiant)
                }

            }

        videoRecord =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    isAddVideoRecord = true
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val filePath =
                        com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
                    fileAboutYou = File(filePath)
                    binding.videoViewProfile.setVideoURI(fileUri)
                }
            }

        gallerySelectRecord =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    isAddVideoRecord = true
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val filePath =
                        com.talktomii.utlis.common.FileUtils.getPath(context, data.data)
                    fileAboutYou = File(filePath)
                    binding.videoViewProfile.setVideoURI(fileUri)
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

    override fun onAdminDetails(admin1: Admin1) {
        progressDialog.dismiss()
        this.admin1 = admin1
        Glide.with(requireContext()).load(admin1.profilePhoto).placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user).into(binding.imgDefault)
        Glide.with(requireContext()).load(admin1.coverPhoto)
            .placeholder(R.drawable.bg_gradient_profile)
            .error(R.drawable.bg_gradient_profile).into(binding.layoutGrandiant)

    }

    override fun onUpdateProfileDetails(admin1: Admin) {
        progressDialog.dismiss()
        var registerModel: RegisterModel? = getUser(prefsManager)
        registerModel!!.admin = admin1
        prefsManager.save(PrefsManager.PREF_PROFILE, registerModel)
        context?.let { showToastMessage(it,getString(R.string.profile_updated_successfully)) }
//        findNavController().popBackStack()
    }

    override fun onUpdatePhoto(admin: com.talktomii.data.photo.Admin) {
        progressDialog.dismiss()
    }

    private fun recordVideo() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = (context as Activity).layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_video_picker, null)
        alertDialogBuilder.setView(dialogView)

        val tvSelectCamera = dialogView.findViewById<TextView>(R.id.tvSelectCamera)
        val tvSelectGallery = dialogView.findViewById<TextView>(R.id.tvSelectGallery)

        alertDialogBuilder.setCancelable(false)
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        tvSelectCamera.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    99
                )
            } else {
                val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                videoRecord.launch(takeVideoIntent)
                alertDialog.dismiss()

            }
            alertDialog.dismiss()

        }

        tvSelectGallery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    99
                )
            } else {

                val i = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                gallerySelectRecord.launch(i)
            }
            alertDialog.dismiss()

        }
    }

    override fun onUpdateAboutYou(admin1: com.talktomii.data.photo.Admin) {
        progressDialog.dismiss()
    }
}