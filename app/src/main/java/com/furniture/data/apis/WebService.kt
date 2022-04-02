package com.furniture.data.apis


import com.furniture.data.model.AllInterst
import com.furniture.data.model.addbookmark.AddBookMarkResponse
import com.furniture.data.model.admin.AdminResponse
import com.furniture.data.model.admin1.AdminResponse1
import com.furniture.data.model.drawer.bookmark.BookMarkResponse
import com.furniture.data.model.getallslotbydate.GetAllSlotByDateResponse
import retrofit2.Response
import retrofit2.http.*


interface WebService {
    companion object {
        private const val SEND_OTP = "user/sendOTP"
        private const val ADD_PHONE = "user/add_phone_number"

        private const val ALL_INTERST = "interest/getAllInterest"
        private const val ALL_INFLUENCE = "admin/get-admins"
        private const val GET_ADMIN = "admin/get-admin"
        private const val ADD_BOOKMARK = "favoriteService/addService"
        private const val REMOVE_BOOKMARK = "favoriteService/deleteService"
        private const val GET_BOOKMARKS = "favoriteService/getService"
        private const val ADD_APPOINMENT = "appointment/addAppointment"
        private const val UPDATE_APPOINMENT = "appointment/updateAppointment/"
        private const val GET_ALL_SLOT_BY_DATE = "appointment/getAllSlotByDate"

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

    @GET(ALL_INTERST)
    suspend fun getSearchInterest(@Query("search") search: String): Response<AllInterst>

    @GET(ALL_INFLUENCE)
    suspend fun getAllAdmin(
        @Header("Authorization") authHeader: String
    ): Response<AdminResponse>

    @GET(ALL_INFLUENCE + "?interest={id}")
    suspend fun getAdminByInterest(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): Response<AdminResponse>

    @GET(GET_ADMIN + "/{id}")
    suspend fun getAdminByID(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): Response<AdminResponse1>

    @POST(ADD_BOOKMARK)
    suspend fun addFavourite(
        @Body id: HashMap<String, Any>,
        @Header("Authorization") authHeader: String
    ): Response<AddBookMarkResponse>

    @DELETE(REMOVE_BOOKMARK + "/{id}")
    suspend fun removeBookmark(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): Response<AddBookMarkResponse>

    @GET(GET_BOOKMARKS)
    suspend fun getBookmarks(
        @Query("uid") id: String,
        @Header("Authorization") authHeader: String
    ): Response<BookMarkResponse>

    @POST(ADD_APPOINMENT)
    suspend fun addAppoinment(
        @Body id: HashMap<String, Any>,
        @Header("Authorization") authHeader: String
    ): Response<BookMarkResponse>

    @POST(UPDATE_APPOINMENT + "/{id}")
    suspend fun updateAppoinment(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): Response<BookMarkResponse>

    @GET(GET_ALL_SLOT_BY_DATE)
    suspend fun getAllSlotByDate(
        @Query("date") date: String,
        @Query("ifid") ifid: String,
    ): Response<GetAllSlotByDateResponse>
}