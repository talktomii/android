package com.furniture.ui.drawer.bookmark

import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.drawer.BookMarkInterface
import com.furniture.utlis.AUTHORIZATION
import com.furniture.utlis.uid
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject


class BookmarkViewModel @Inject constructor(private val webService: WebService) : ViewModel() {

    var commonInterface: CommonInterface? = null
    var onbookmarkinterface: BookMarkInterface? = null

    fun getBookmarks(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getBookmarks(uid, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        onbookmarkinterface?.onBookmarkAdmins(authResponse.body()!!.payload)
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

}


