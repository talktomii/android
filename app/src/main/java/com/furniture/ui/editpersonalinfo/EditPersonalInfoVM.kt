package com.furniture.ui.editpersonalinfo

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.furniture.R
import javax.inject.Inject

class EditPersonalInfoVM @Inject constructor() : ViewModel() {


    fun onClick(view:View){
        when(view.id){
            R.id.ivBack, R.id.tvBack -> view.findNavController().popBackStack()
        }
    }
}