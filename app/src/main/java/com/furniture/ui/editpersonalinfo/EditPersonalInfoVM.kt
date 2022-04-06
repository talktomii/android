package com.furniture.ui.editpersonalinfo

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.furniture.R
import com.furniture.data.apis.WebService
import com.furniture.data.model.admin1.Admin1
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.UpdatePhotoInterface
import com.furniture.utlis.AUTHORIZATION
import com.furniture.utlis.uid
import com.google.android.gms.common.api.ApiException
import com.makeramen.roundedimageview.RoundedImageView
import okhttp3.RequestBody
import javax.inject.Inject

class EditPersonalInfoVM @Inject constructor(private val webService: WebService) : ViewModel() {
    var commonInterface: CommonInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var updatePhotoInterface: UpdatePhotoInterface? = null
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
                        adminDetailInterface?.onAdminDetails(authResponse.body()!!.payload.admin[0])
                        userField.set(authResponse.body()!!.payload.admin[0])
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

    fun updatePhoto(
        profilePhoto: HashMap<String, RequestBody>,
    ) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updatePhoto(uid, profilePhoto, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            updatePhotoInterface?.onUpdatePhoto(authResponse.body()!!.payload.admin)
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