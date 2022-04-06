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
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.furniture.R
import com.furniture.data.model.admin1.Admin1
import com.furniture.databinding.EditPersonalInfoFragmentBinding
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.ui.home.profile.AdapterAvailability
import com.furniture.ui.home.profile.AdapterInterests
import com.furniture.ui.home.profile.AdapterMySocialMedias
import com.furniture.ui.home.profile.AdapterPrice
import com.furniture.utlis.dialogs.ProgressDialog
import com.furniture.utlis.uid
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.android.support.DaggerFragment
import java.io.InputStream
import javax.inject.Inject


class EditPersonalInfo : DaggerFragment(R.layout.edit_personal_info_fragment), AdminDetailInterface,
    CommonInterface {
    private lateinit var binding: EditPersonalInfoFragmentBinding

    lateinit var profileImg_launcher: ActivityResultLauncher<Intent>
    lateinit var coverImg_launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var viewModel: EditPersonalInfoVM

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
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
                    val inputStream: InputStream =
                        requireActivity().contentResolver.openInputStream(fileUri)!!
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
                    binding.layoutGrandiant.background = image
                }

            }

        setListeners(view)
        initadapters()
    }

    private fun initadapters() {
        binding.rvPrice.adapter = AdapterPrice()
        binding.rvInterest.adapter = AdapterInterests()
        binding.rvAvailability.adapter = AdapterAvailability()
        binding.rvSocialMedia.adapter = AdapterMySocialMedias(requireContext())
    }

    private fun setListeners(view: View) {

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
            var data: HashMap<String, Any> = hashMapOf()
            data.put("name", binding.etFirstName.text.toString())
            data.put("userName", binding.etUsername.text.toString())
            viewModel.updateProfile(data)
        }

        binding.ivInterest.setOnClickListener {
            view.findNavController().navigate(R.id.action_editPersonalInfo_to_editInterestFragment)
        }

        binding.txtBudgesCount.setOnClickListener {
            view.findNavController().navigate(R.id.action_editPersonalInfo_to_myBudgesFragment)
        }

        binding.txtAddPrice.setOnClickListener {
            val bottomsheet = AddPriceBottomSheetFragment(object : AddPriceInterface {
                override fun addprice() {

                }
            })
            bottomsheet.show(childFragmentManager, "addprice")
        }
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())
        viewModel.adminDetailInterface = this
        viewModel.commonInterface = this
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

//        Glide.with(requireContext()).load(admin1.profilePhoto).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(binding.imgDefault)
//        if (admin1.coverPhoto != null) {
//            val url = URL(admin1.coverPhoto)
//            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            val image: Drawable = BitmapDrawable(requireContext().resources, bitmap)
//            binding.layoutGrandiant.background = image
//        } else {
//            binding.layoutGrandiant.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_gradient_profile)
//        }

        (binding.rvPrice.adapter as AdapterPrice).setItemList(viewModel.userField.get()!!.price)
        (binding.rvInterest.adapter as AdapterInterests).setItemList(viewModel.userField.get()!!.interest)
        (binding.rvAvailability.adapter as AdapterAvailability).setItemList(viewModel.userField.get()!!.availaibility)
        (binding.rvSocialMedia.adapter as AdapterMySocialMedias).setItemList(viewModel.userField.get()!!.socialNetwork)

    }


}