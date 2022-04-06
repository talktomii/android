package com.furniture.utlis


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.R
import com.furniture.databinding.DialogCameraGalleryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OpenCameraVideoFragment(var cameravideo: CameraGalleryCallback, val firstVal: String, val secondval: String, var tittle: String? = "") : BottomSheetDialogFragment() {
    private lateinit var binding: DialogCameraGalleryBinding
    /*  @SuppressLint("RestrictedApi")
      override fun setupDialog(dialog: Dialog, style: Int) {
          dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
          super.setupDialog(dialog, R.style.MyBottomSheetDialogTheme)
          val view = LayoutInflater.from(context).inflate(R.layout.dialog_camera_gallery, null)
          dialog.setContentView(view)
      }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCameraGalleryBinding.inflate(LayoutInflater.from(context))
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCamera.text = firstVal
        binding.tvGallery.text = secondval
        if (!tittle.isNullOrEmpty())
            binding.tvReplacemnt.text = tittle

        binding.tvCamera.setOnClickListener {
            cameravideo.cameraClick()
            dismiss()
            /*  requireContext().cameraPermission(object : PermissionCallback {
                  override fun permissionGranted() {

                  }

                  override fun permissionRejected() {

                  }

              })*/

        }
        binding.tvGallery.setOnClickListener {
            cameravideo.galleryClick()
            dismiss()
            /*  requireContext().galleryclick(object : PermissionCallback {
                  override fun permissionGranted() {
                      cameravideo.galleryClick()
                      dismiss()
                  }

                  override fun permissionRejected() {

                  }

              })*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }


}