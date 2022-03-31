package com.talktomii.ui.loginSignUp.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.talktomii.databinding.FragmentCreateProfileBinding
import com.talktomii.utlis.ImageUtils
import com.talktomii.utlis.setImageFromFile
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CreateProfileFragment : DaggerFragment() {

    private val viewModels by viewModels<CreateProfileVM>()

    @Inject
    lateinit var viewModel: CreateProfileVM

    private var image: String? = null

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
        binding.btnNEXT.setOnClickListener {
            radioCheck()

        }

        setListener()
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
                    image = fileToUploadNew?.absolutePath ?: ""
                    binding.imgDefault.setImageFromFile(fileToUploadNew)

                }

                ImageUtils.REQ_CODE_GALLERY_PICTURE_PROFILE -> {
                    val fileToUploadNew = data?.data?.let {
                        ImageUtils.getImagePathFromGallery(requireContext(), it)

                    }

                    image = fileToUploadNew?.absolutePath ?: ""
                    binding.imgDefault.setImageFromFile(fileToUploadNew)

                }
                ImageUtils.REQ_CODE_CAMERA_PICTURE_COVER -> {
                    val fileToUploadNew = let { ImageUtils.getFile(requireContext()) }
                    image = fileToUploadNew?.absolutePath ?: ""
                    binding.coverImage.setImageFromFile(fileToUploadNew)

                }

                ImageUtils.REQ_CODE_GALLERY_PICTURE_COVER -> {
                    val fileToUploadNew = data?.data?.let {
                        ImageUtils.getImagePathFromGallery(requireContext(), it)

                    }
                    image = fileToUploadNew?.absolutePath ?: ""
                    binding.coverImage.setImageFromFile(fileToUploadNew)

                }
            }
        }


    }

    fun radioCheck(){

            val userOrInfluencer = when {
                binding.radioUser.isChecked ->true
                binding.radioInfluencer.isChecked -> false
                else -> null
            }

            view?.findNavController()?.navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToTellUsMore(isUser = userOrInfluencer?:false))

    }
}

