package com.furniture.data.apis


import com.furniture.data.model.AllInterst
import retrofit2.Response
import retrofit2.http.GET


interface WebService {
    companion object {
        private const val SEND_OTP = "user/sendOTP"
        private const val ADD_PHONE = "user/add_phone_number"

        private const val ALL_INTERST = "interest/getAllInterest"
        private const val ALL_INFLUENCE = "admin/get-admins"

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

    @GET(ALL_INTERST)
    suspend fun getAllInterest(): Response<AllInterst>


    @GET(ALL_INFLUENCE)
    suspend fun getInfluence(): Response<AllInterst>

}