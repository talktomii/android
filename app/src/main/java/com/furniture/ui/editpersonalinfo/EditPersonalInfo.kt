package com.furniture.ui.editpersonalinfo

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.furniture.R
import com.furniture.data.model.admin1.Admin1
import com.furniture.databinding.EditPersonalInfoFragmentBinding
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.utlis.dialogs.ProgressDialog
import com.furniture.utlis.uid
import dagger.android.support.DaggerFragment
import java.net.URL
import javax.inject.Inject


class EditPersonalInfo : DaggerFragment(R.layout.edit_personal_info_fragment), AdminDetailInterface,
    CommonInterface {
    private lateinit var binding: EditPersonalInfoFragmentBinding


    @Inject
    lateinit var viewModel: EditPersonalInfoVM

    private lateinit var progressDialog: ProgressDialog

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

        progressDialog.dismiss()
    }
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(EditPersonalInfoVM::class.java)
//    }

}