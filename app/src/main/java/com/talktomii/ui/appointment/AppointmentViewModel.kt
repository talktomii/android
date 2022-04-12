package com.talktomii.ui.appointment

import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.google.gson.GsonBuilder
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.ErrorModelClass
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.AddAppointmentInterface
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.FailureAPI400
import java.io.IOException
import javax.inject.Inject


class AppointmentViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var addAppointment: AddAppointmentInterface? = null
    var commonInterface: CommonInterface? = null
    var apiFailure: FailureAPI400? = null

    fun addAppointment(hashMap: HashMap<String, Any>) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse =
                    webService.addAppoinment(hashMap)

                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        addAppointment?.onAddAppointment(authResponse.body())
                    }
                } else {
                    if (authResponse.code() === 400) {
                        val gson = GsonBuilder().create()
                        var mError = ErrorModelClass()
                        try {
                            mError = gson.fromJson(
                                authResponse.errorBody()!!.string(),
                                ErrorModelClass::class.java
                            )
                            apiFailure!!.onFailureAPI400(mError.message)

                        } catch (e: IOException) {
                            // handle failure to read error
                        }
                    } else {
                        commonInterface!!.onFailureAPI(authResponse.message())

                    }

                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

}


