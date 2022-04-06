package com.talktomii.data.apis


import com.example.example.PayloadCards
import com.talktomii.data.model.AllInterst
import com.talktomii.data.model.addbookmark.AddBookMarkResponse
import com.talktomii.data.model.admin.AdminResponse
import com.talktomii.data.model.admin1.AdminResponse1
import com.talktomii.data.model.drawer.bookmark.BookMarkResponse
import com.talktomii.data.model.getallslotbydate.GetAllSlotByDateResponse
import com.talktomii.data.model.photo.UpdatePhoto
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.ui.mycards.data.addCardData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface WebService {
    companion object {
        private const val ADD_CARD = "card/addCard"
        private const val DELETE_CARD_ITEM = "card/deleteCard/"
        private const val GET_CARDS = "card/getCardByuid/623dac1b6ee1ef298842b9ad"
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
        private const val UPDATE_PHOTO = "admin/updatePhoto"
        private const val UPDATE_ADMIN = "admin/updateAdmin"

    }

    @GET(GET_CARDS)
    fun getCards(
        @Header("Authorization") auth: String
    ): Call<PayloadCards>

    @POST(ADD_CARD)
    @FormUrlEncoded
    fun addCard(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addCardData>>

    @DELETE("card/deleteCard/{id}")
    fun deleteCard(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<ApiResponse<Any>>

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

    @Multipart
    @PUT("$UPDATE_PHOTO/{id}")
    suspend fun updatePhoto(
        @Path("id") id: String,
        @PartMap map: HashMap<String, RequestBody>,
        @Header("Authorization") authHeader: String
    ): Response<UpdatePhoto>

    @PUT("$UPDATE_ADMIN/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body data: HashMap<String, Any>,
        @Header("Authorization") authHeader: String
    ): Response<AdminResponse1>


}