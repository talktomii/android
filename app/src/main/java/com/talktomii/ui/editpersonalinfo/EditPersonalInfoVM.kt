package com.talktomii.ui.editpersonalinfo

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.AdminDetailInterface
import com.talktomii.interfaces.CommonInterface
import com.talktomii.utlis.AUTHORIZATION
import com.talktomii.utlis.uid
import com.google.android.gms.common.api.ApiException
import com.makeramen.roundedimageview.RoundedImageView
import javax.inject.Inject

class EditPersonalInfoVM @Inject constructor(private val webService: WebService) : ViewModel() {
    var commonInterface: CommonInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var userField = ObservableField<Admin1>()

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack, R.id.tvBack -> view.findNavController().popBackStack()
        }
    }

    fun getAdminById(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAdminByID(string, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        userField.set(authResponse.body()!!.payload.admin[0])
                        adminDetailInterface?.onAdminDetails(authResponse.body()!!.payload.admin[0])
                    }
                } else {
                    commonInterface!!.onFailureAPI(authResponse.message())
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun updateProfile(data: HashMap<String, Any>) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updateProfile(uid, data, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            adminDetailInterface?.onAdminDetails(authResponse.body()!!.payload.admin[0])
                        }
                    }
                } else {
                    commonInterface!!.onFailure(authResponse.message())
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: RoundedImageView, url: Admin1?) {
        if (url != null) {
            Glide.with(view.context).load(url.profilePhoto).into(view)
        }
    }

}