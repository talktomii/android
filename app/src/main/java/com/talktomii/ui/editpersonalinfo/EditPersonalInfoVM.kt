package com.talktomii.ui.editpersonalinfo

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.Interest
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.AdminDetailInterface
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.UpdatePhotoInterface
import com.talktomii.utlis.uid
import com.google.android.gms.common.api.ApiException
import com.makeramen.roundedimageview.RoundedImageView
import okhttp3.RequestBody
import javax.inject.Inject

class EditPersonalInfoVM @Inject constructor(private val webService: WebService) : ViewModel() {
    var commonInterface: CommonInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var userField = ObservableField<Admin1>()
    var updatePhotoInterface: UpdatePhotoInterface? = null

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack, R.id.tvBack -> view.findNavController().popBackStack()
        }
    }

    fun getAdminById(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAdminByID(string)
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

    fun updateProfile(data: String, _id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updateProfile(_id, data)
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
        _id: String,
    ) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updatePhoto(_id, profilePhoto)
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

    fun updateInterestList(arrayList: ArrayList<Interest>) {
        userField.get()!!.interest.clear()
        val admin1 = userField.get()
        admin1?.interest?.addAll(arrayList.filter { interest -> interest.isClicked })

        userField.set(admin1)

    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: RoundedImageView, url: Admin1?) {
        if (url != null) {
            Glide.with(view.context).load(url.profilePhoto).into(view)
        }
    }

}