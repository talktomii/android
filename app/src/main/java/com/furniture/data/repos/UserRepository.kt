package com.furniture.data.repos

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import com.furniture.utlis.PrefsManager
import com.furniture.data.apis.WebService
import com.furniture.data.model.UserData
import com.furniture.utlis.USER_DATA
import com.furniture.ApplicationComponent
import com.furniture.data.model.PushData
import com.furniture.utlis.PUSH_DATA
import com.furniture.utlis.USER_LANGUAGE

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(
    private val app: ApplicationComponent,
    private val prefsManager: PrefsManager, private val webService: WebService
) {

    val groupCreatedCall = MutableLiveData<String>()
    val loginGuestUser = MutableLiveData<String>()
    val groupExitResponse = MutableLiveData<Pair<Boolean, String>>()
    val pushData = MutableLiveData<PushData>()
    val isNewNotification = MutableLiveData<Boolean>()


    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected!!
    }

    fun isUserLoggedIn(): Boolean {
        val user = prefsManager.getObject(USER_DATA, UserData::class.java)
        user?.id?.let {
            return true
        }
        return false
    }

    fun getAuthToken(): String {
        return "Bearer ${prefsManager.getString(PrefsManager.PREF_API_TOKEN, "").replace("\"", "")}"
    }


    fun getUser(): UserData? {
        return prefsManager.getObject(USER_DATA, UserData::class.java)
    }


    fun getUserLanguage(): String {
        return prefsManager.getString(USER_LANGUAGE, "en")
    }

    fun getPushCallData(): PushData? {
        return prefsManager.getObject(PUSH_DATA, PushData::class.java)
    }

    fun pushTokenUpdate(token: String) {
        val hashMap = HashMap<String, String>()
        hashMap["deviceToken"] = token

        /*webService.updatePushToken(getEncryptedData(hashMap))
            .enqueue(object : Callback<ApiResponse<Any>> {

                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        Log.e("fcmToken", "Success")
                    } else {
                        Log.e("fcmToken", "Faliure")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, throwable: Throwable) {
                    Log.e("fcmToken", "faliue 500")
                }
            })*/
    }


}

