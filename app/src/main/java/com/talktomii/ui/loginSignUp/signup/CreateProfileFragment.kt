package com.talktomii.ui.loginSignUp.signup

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.talktomii.R
import com.talktomii.data.model.Role
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.FragmentCreateProfileBinding
import com.talktomii.databinding.FragmentSignUpBinding
import com.talktomii.ui.loginSignUp.LoginViewModel
import com.talktomii.utlis.*
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class CreateProfileFragment : DaggerFragment() {

    private lateinit var selectedRole: Role

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
        binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.imgDefault.setImageResource(R.drawable.ic_user_dark_profile)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.imgDefault.setImageResource(R.drawable.ic_user)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireActivity())
        viewModel.getRoles()

        binding.btnNEXT.setOnClickListener {
            viewModel.checkUserName(binding.txtUserName.text.toString())

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
//                    val userOrInfluencer = when {
//                        binding.radioUser.isChecked -> true
//                        binding.radioInfluencer.isChecked -> false
//                        else -> null
//                    }
                    prefsManager.save(PrefsManager.PREF_API_TOKEN, it.data?.token)
                    prefsManager.save(PrefsManager.PREF_PROFILE, it.data)
                    requireContext().showMessage("Register  Successfully")
                    var userOrInfluencer = selectedRole.roleName == "user"
                    view?.findNavController()?.navigate(
                        CreateProfileFragmentDirections.actionCreateProfileFragmentToTellUsMore(
                            isUser = userOrInfluencer ?: false
                        )
                    )
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

        viewModel.role.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
                    binding.rvRole.adapter = AdapterRole(it.data?.allRole ?: arrayListOf(), this)
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

        viewModel.checkUserName.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    radioCheck()
                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
//                    requireContext().showMessage("Username already exists. Please enter unique username")
//                    Log.e("MESSAGE",it.message?:"")
                    binding.btnNEXT.showSnackBar("Username already exists. Please enter unique username")

//                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }
            }
        })
    }

    private fun setListener() {
        binding.ivCamera.setOnClickListener {
            ImageUtils.displayImagePicker(this, requireFragmentManager(),true)
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

        if (validation(
                firstname = binding.txtFirstName.text.toString(),
                profilePhoto = imageProfile.toString(),
                lastname = binding.txtLastName.text.toString(),
                username = binding.txtUserName.text.toString()
            )
        ) {
            val map: HashMap<String, RequestBody> = HashMap()
            map["fname"] = "${binding.txtFirstName.text.toString()}".toRequestBody(
                    "text/plain".toMediaTypeOrNull()
                )
            map["lname"]=" ${binding.txtLastName.text.toString()}".toRequestBody(
                    "text/plain".toMediaTypeOrNull()
                )

            map["email"] =
                "${requireArguments()["email"].toString()}".toRequestBody("text/plain".toMediaTypeOrNull())
            if(requireArguments()["isSocial"]!=null){
                map["isSocial"] = "true".toRequestBody("text/plain".toMediaTypeOrNull())
            }else {
                map["password"] =
                    "${requireArguments()["password"].toString()}".toRequestBody("text/plain".toMediaTypeOrNull())
                map["isSocial"] = "false".toRequestBody("text/plain".toMediaTypeOrNull())
            }
            map["role"] = "${selectedRole._id}".toRequestBody("text/plain".toMediaTypeOrNull())
            map["userName"] =
                "${binding.txtUserName.text.toString()}".toRequestBody("text/plain".toMediaTypeOrNull())

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

    }

    private fun validation(
        profilePhoto: String,
        firstname: String,
        lastname: String,
        username: String
    ): Boolean {
        return when {
            profilePhoto.isNullOrEmpty() -> {
                binding.imgDefault.showSnackBar("please upload profile photo")
                false
            }
            firstname.isNullOrEmpty() -> {
                binding.txtFirstName.showSnackBar("please enter first name")
                false
            }
            lastname.isNullOrEmpty() -> {
                binding.txtLastName.showSnackBar("please enter last name")
                false
            }
            username.isNullOrEmpty() -> {
                binding.txtUserName.showSnackBar("Please enter unique username")
                false

            }
            else -> true
        }
    }


    fun onRoleChanged(role: Role) {
        selectedRole = role
    }
}

