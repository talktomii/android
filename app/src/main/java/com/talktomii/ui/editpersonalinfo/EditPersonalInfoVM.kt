package com.talktomii.ui.editpersonalinfo

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ApiException
import com.makeramen.roundedimageview.RoundedImageView
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.Interest
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.*
import okhttp3.RequestBody
import javax.inject.Inject

class EditPersonalInfoVM @Inject constructor(private val webService: WebService) : ViewModel() {
    var commonInterface: CommonInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var onUpdateProfileInterface: UpdateProfileInterface? = null
    var updateAvailability: UpdateAvaibilityInterface? = null
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
                    commonInterface!!.onFailureAPI(
                        authResponse.message(),
                        authResponse.code(),
                        authResponse.errorBody()
                    )
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun updateProfile(data: HashMap<String, Any>, _id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updateProfile(_id, data)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            onUpdateProfileInterface?.onUpdateProfileDetails(authResponse.body()!!.payload.admin)
                        }
                    }
                } else {
                    commonInterface!!.onFailureAPI(
                        authResponse.message(),
                        authResponse.code(),
                        authResponse.errorBody()
                    )
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun updateAvailabilityTime(
        data: HashMap<String, Any>,
        position: Int,
        model: Availaibility,
        which: Int
    ) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updateAvailability(data)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            updateAvailability?.onUpdateAvibility(position, model, which)
                        }
                    }
                } else {
                    commonInterface!!.onFailureAPI(
                        authResponse.message(),
                        authResponse.code(),
                        authResponse.errorBody()
                    )
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }


    fun deleteAvailabilityTime(
        position: Int,
        id: String,
        uid: String,
        which: Int
    ) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.deleteAvailability(id,uid)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            updateAvailability?.onUpdateAvibility(position, null, which)
                        }
                    }
                } else {
                    commonInterface!!.onFailureAPI(
                        authResponse.message(),
                        authResponse.code(),
                        authResponse.errorBody()
                    )
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
                    commonInterface!!.onFailureAPI(
                        authResponse.message(),
                        authResponse.code(),
                        authResponse.errorBody()
                    )
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