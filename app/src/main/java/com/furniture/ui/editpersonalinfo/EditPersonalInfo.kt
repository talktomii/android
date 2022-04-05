package com.furniture.ui.editpersonalinfo

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.furniture.R
import com.furniture.data.model.admin1.Admin1
import com.furniture.data.model.photo.Admin
import com.furniture.databinding.EditPersonalInfoFragmentBinding
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.UpdatePhotoInterface
import com.furniture.utlis.dialogs.ProgressDialog
import com.furniture.utlis.uid
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import java.net.URL
import javax.inject.Inject


class EditPersonalInfo : DaggerFragment(R.layout.edit_personal_info_fragment), AdminDetailInterface,
    CommonInterface , UpdatePhotoInterface {
    private lateinit var binding: EditPersonalInfoFragmentBinding

    lateinit var profileImg_launcher: ActivityResultLauncher<Intent>
    lateinit var coverImg_launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var viewModel: EditPersonalInfoVM

    private lateinit var progressDialog: ProgressDialog
    private var fileProfile: File? = null
    private var fileCoverPhoto: File? = null
    private var isChangeProfile = false
    private var isChangeUserData = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditPersonalInfoFragmentBinding.inflate(inflater, container, false)
//        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        profileImg_launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode

                if (resultCode == Activity.RESULT_OK) {
                    isChangeProfile = true
                    val data = result.data
                    val filePath =
                        com.furniture.utlis.common.FileUtils.getPath(context, data!!.data)
                    fileProfile = File(filePath)
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data.data!!
                    Glide.with(requireContext()).load(fileUri).into(binding.imgDefault)
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
                        com.furniture.utlis.common.FileUtils.getPath(context, data.data)
                    fileCoverPhoto = File(filePath)
                    val inputStream: InputStream =
                        requireActivity().contentResolver.openInputStream(fileUri)!!
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
                    binding.layoutGrandiant.background = image
                }

            }

        setListeners()
    }

    private fun setListeners() {

        binding.ivCamera.setOnClickListener {
            ImagePicker.with(this).createIntent { intent ->
                profileImg_launcher.launch(intent)
            }
        }

        binding.imgCam.setOnClickListener {
            ImagePicker.with(this).createIntent { intent ->
                coverImg_launcher.launch(intent)
            }
        }

        binding.tvSave.setOnClickListener {

            if (isChangeProfile){
                val map: HashMap<String, RequestBody> = HashMap()
                if (fileProfile != null) {
                    val body = fileProfile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    map["profilePhoto\"; filename=\"imageProfile.png\" "] = body
                }
                if (fileCoverPhoto != null) {
                    val body = fileCoverPhoto!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    map["coverPhoto\"; filename=\"imageCover.png\" "] = body
                }
                viewModel.updatePhoto(map)
            }
            if (isChangeUserData){
                var data: HashMap<String, Any> = hashMapOf()
                data["name"] = binding.etFirstName.text.toString()
                data["userName"] = binding.etUsername.text.toString()
                viewModel.updateProfile(data)
            }

        }

    }


    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
        viewModel.adminDetailInterface = this
        viewModel.commonInterface = this
        viewModel.updatePhotoInterface = this
        binding.viewModel = viewModel
        viewModel.getAdminById(uid)
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


    override fun onAdminDetails(admin1: Admin1) {
        progressDialog.dismiss()

        Glide.with(requireContext()).load(admin1.profilePhoto).placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user).into(binding.imgDefault)
        if (admin1.coverPhoto != null) {
            val url = URL(admin1.coverPhoto)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
            binding.layoutGrandiant.background = image
        } else {
            binding.layoutGrandiant.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_gradient_profile)
        }

    }

    override fun onUpdatePhoto(admin: Admin) {
        progressDialog.dismiss()
    }
}