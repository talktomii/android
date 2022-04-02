package com.furniture.ui.appointment

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.HomeInterface
import com.furniture.utlis.AUTHORIZATION
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject


class AppointmentViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var homeInterface: HomeInterface? = null
    var commonInterface: CommonInterface? = null
    var bookMark = ObservableField<Boolean>()
//
//    fun addAppointment() {
//        commonInterface!!.onStarted()
//        Coroutines.main {
//            try {
//                var hashMap: HashMap<String, Any> = hashMapOf()
////                hashMap.put("ifid", userField.get()!!._id)
////                hashMap.put("uid", userField.get()!!._id)
////                hashMap.put("date", userField.get()!!._id)
////                hashMap.put("startTime", userField.get()!!._id)
////                hashMap.put("endTime", userField.get()!!._id)
////                hashMap.put("duration", userField.get()!!._id)
//                val authResponse =
//                    webService.addAppoinment(hashMap,AUTHORIZATION)
//
//                if (authResponse.isSuccessful) {
//                    authResponse.body().let {
//                        homeInterface?.onHomeAdmins(authResponse.body()!!.payload)
//                    }
//                } else {
//                    commonInterface!!.onFailureAPI(authResponse.message())
//                }
//            } catch (e: ApiException) {
//                e.message?.let { commonInterface!!.onFailure(it) }
//            } catch (ex: Exception) {
//                ex.message?.let { commonInterface!!.onFailure(it) }
//            }
//        }
//    }

}


