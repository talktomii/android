package com.furniture.data.apis


import com.furniture.data.model.*
import com.furniture.data.network.responseUtil.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface WebService {
    companion object {
        private const val SEND_OTP = "user/sendOTP"
        private const val ADD_PHONE = "user/add_phone_number"


    }


//    @FormUrlEncoded
//    @POST(SOCIAL_LOGIN)
//    fun socialLogin(@FieldMap hashMap: HashMap<String, String>): Call<ApiResponse<UserData>>



//    @GET("https://graph.instagram.com/{user_id}")
//    fun getInstaUserApis(
//        @Path("user_id") user_id: String,
//        @Query("fields") fields: String,
//        @Query("access_token") access_token: String
//    ): Call<UserData>



}