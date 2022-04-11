package com.talktomii.ui.appointment

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.talktomii.data.apis.WebService
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.HomeInterface
import java.util.HashMap
import javax.inject.Inject


class AppointmentViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var homeInterface: HomeInterface? = null
    var commonInterface: CommonInterface? = null
    var bookMark = ObservableField<Boolean>()

    fun addAppointment(hashMap: HashMap<String, Any>) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
//                hashMap.put("ifid", userField.get()!!._id)
//                hashMap.put("uid", userField.get()!!._id)
//                hashMap.put("date", userField.get()!!._id)
//                hashMap.put("startTime", userField.get()!!._id)
//                hashMap.put("endTime", userField.get()!!._id)
//                hashMap.put("duration", userField.get()!!._id)
                val authResponse =
                    webService.addAppoinment(hashMap)

                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        homeInterface?.onHomeAdmins(authResponse.body()!!.payload)
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

}


