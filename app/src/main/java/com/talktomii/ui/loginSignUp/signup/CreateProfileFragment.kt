package com.talktomii.ui.loginSignUp.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.FragmentCreateProfileBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.ImageUtils
import com.talktomii.utlis.PrefsManager
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.utlis.setImageFromFile
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class CreateProfileFragment : DaggerFragment() {

    private val viewModels by viewModels<CreateProfileVM>()

    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var prefsManager: PrefsManager

    private var imageProfile: String? = null
    private var imageCover: String? = null
    private lateinit var progressDialog: ProgressDialog

    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog= ProgressDialog(requireActivity())
        binding.btnNEXT.setOnClickListener {
            radioCheck()

        }

//        var email=requireArguments()["email"].toString()
//        var password =requireArguments()["password"].toString()
        setListener()
        addObserver()
    }

    private fun addObserver() {
        viewModel.createProfile.observe(requireActivity(), Observer {
            it ?: return@Observer

            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    val userOrInfluencer = when {
                        binding.radioUser.isChecked -> true
                        binding.radioInfluencer.isChecked -> false
                        else -> null
                    }
                    view?.findNavController()?.navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToTellUsMore(isUser = userOrInfluencer?:false))
                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }
            }
        })

    }

    private fun setListener() {
        binding.ivCamera.setOnClickListener {
            ImageUtils.displayImagePicker(this, requireFragmentManager(), true)
        }

        binding.imgCam.setOnClickListener {
            ImageUtils.displayImagePicker(this, requireFragmentManager())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                ImageUtils.REQ_CODE_CAMERA_PICTURE_PROFILE -> {
                    val fileToUploadNew = let { ImageUtils.getFile(requireContext()) }
                    imageProfile = fileToUploadNew?.absolutePath ?: ""
                    binding.imgDefault.setImageFromFile(fileToUploadNew)

                }

                ImageUtils.REQ_CODE_GALLERY_PICTURE_PROFILE -> {
                    val fileToUploadNew = data?.data?.let {
                        ImageUtils.getImagePathFromGallery(requireContext(), it)

                    }

                    imageProfile = fileToUploadNew?.absolutePath ?: ""
                    binding.imgDefault.setImageFromFile(fileToUploadNew)

                }

                ImageUtils.REQ_CODE_CAMERA_PICTURE_COVER -> {
                    val fileToUploadNew = let { ImageUtils.getFile(requireContext()) }
                    imageCover = fileToUploadNew?.absolutePath ?: ""
                    binding.coverImage.setImageFromFile(fileToUploadNew)

                }

                ImageUtils.REQ_CODE_GALLERY_PICTURE_COVER -> {
                    val fileToUploadNew = data?.data?.let {
                        ImageUtils.getImagePathFromGallery(requireContext(), it)

                    }
                    imageCover = fileToUploadNew?.absolutePath ?: ""
                    binding.coverImage.setImageFromFile(fileToUploadNew)

                }
            }
        }
    }

    fun radioCheck() {

        if (vailidation()) {
            val map: HashMap<String, RequestBody> = HashMap()
            map["name"] =
                "${binding.txtFirstName.text.toString()} ${binding.txtLastName.text.toString()}".toRequestBody(
                    "text/plain".toMediaTypeOrNull()
                )

            map["email"] =
                "${requireArguments()["email"].toString()}".toRequestBody("text/plain".toMediaTypeOrNull())
            map["password"] =
                "${requireArguments()["password"].toString()}".toRequestBody("text/plain".toMediaTypeOrNull())
            map["role"] = "2874823".toRequestBody("text/plain".toMediaTypeOrNull())
            map["userName"] =
                "${binding.txtUserName.text.toString()}".toRequestBody("text/plain".toMediaTypeOrNull())
            map["isSocial"] = "false".toRequestBody("text/plain".toMediaTypeOrNull())
            map["termsAndConditionIsTrue"] = "true".toRequestBody("text/plain".toMediaTypeOrNull())
            map["above18IsTrue"] = "true".toRequestBody("text/plain".toMediaTypeOrNull())

            if (!imageCover.isNullOrEmpty()) {
                val body = File(imageCover).asRequestBody("image/jpeg".toMediaTypeOrNull())
                map["coverPhoto\"; filename=\"imageCover.png\" "] = body
            }
            if (!imageProfile.isNullOrEmpty()) {
                val body = File(imageProfile).asRequestBody("image/jpeg".toMediaTypeOrNull())
                map["profilePhoto\"; filename=\"imageProfile.png\" "] = body
            }
            viewModel.createProfile(map)
        }
//

    }

    private fun vailidation(): Boolean {
        return true
    }
}

