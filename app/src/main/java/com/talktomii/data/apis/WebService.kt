package com.talktomii.data.apis

import com.example.example.PayloadCards
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.ui.banksettings.models.BankData
import com.talktomii.ui.banksettings.models.addBankData
import com.talktomii.ui.callhistory.models.CallHistoryData
import com.talktomii.ui.coupon.models.CouponData
import com.talktomii.ui.home.notifications.models.NotificationData
import com.talktomii.ui.mycards.data.addCardData
import com.talktomii.ui.mycards.model.PaymentPayload
import com.talktomii.ui.mywallet.models.CurrentWalletPaylod
import com.talktomii.ui.mywallet.models.WalletPayload
import com.talktomii.ui.mywallet.models.addWalletData
import com.talktomii.ui.reportabuse.models.AddReport
import com.talktomii.ui.reportabuse.models.ReportAbuseData
import retrofit2.Call
import com.talktomii.data.model.AllInterst
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.model.RolesModel
import com.talktomii.data.model.addbookmark.AddBookMarkResponse
import com.talktomii.data.model.admin.AdminResponse
import com.talktomii.data.model.admin1.AdminResponse1
import com.talktomii.data.model.drawer.bookmark.BookMarkResponse
import com.talktomii.data.model.getallslotbydate.GetAllSlotByDateResponse
import com.talktomii.data.photo.UpdatePhoto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface WebService {
    companion object {
        private const val ADD_CARD = "card/addCard"
        private const val REGISTER = "admin/signup"
        private const val LOGIN = "admin/login"
        private const val GET_ALL_ROLE = "role"
        private const val DELETE_CARD_ITEM = "card/deleteCard/"
        private const val ADD_WALLET = "walletHistory/addWalletHistory"
        private const val ADD_BANK = "bank/addBank"
        private const val ADD_COUPON = "coupon/addCoupon"
        private const val GET_CALL_HISTORY = "callHistory/getCallHistoryByuid"
        private const val DELETE_CALL_HISTORY = "appointment/deleteCallHistory"
        private const val BLOCK_USER = "admin/blockUser"
        private const val GET_NOTIFICATION = "notification/getNotification"
        private const val GET_REPORTABUSE_TYPE = "reportAbuse/getReportAbuse"
        private const val ADD_FEEDBACK = "feedback/addFeedback"

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

    @GET("card/getCardByuid/{id}")
    fun getCards(
        @Path("id") id: String?,
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

    @POST(ADD_WALLET)
    @FormUrlEncoded
    fun addWallet(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addWalletData>>

    @GET("walletHistory/getWalletHistoryByuid/{id}")
    fun getWallet(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<WalletPayload>

    @Multipart
    @POST(REGISTER)
    fun createProfile(
        @PartMap map: HashMap<String, RequestBody>
    ): Call<ApiResponse<RegisterModel>>


    @FormUrlEncoded
    @POST(LOGIN)
    fun loginApi(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<RegisterModel>>

    @GET(GET_ALL_ROLE)
    fun getRole(
    ): Call<ApiResponse<RolesModel>>

    @GET("walletHistory/getCurrentAmount/{id}")
    fun getCurrentAmount(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<CurrentWalletPaylod>

    @GET("walletHistory/getWalletHistoryByuid/{id}")
    fun getPayment(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<PaymentPayload>

    @POST(ADD_BANK)
    @FormUrlEncoded
    fun addBank(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addBankData>>

    @GET("bank/getBankByuid/{id}")
    fun getBank(
        @Path("id") id: String?,
        @Header("Authorization") auth: String,
    ): Call<BankData>

    @PUT("bank/updateBank/{id}")
    @FormUrlEncoded
    fun updateBank(
        @Path("id") id: String?,
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<Any>>

    @DELETE("bank/deleteBank/{id}")
    fun deleteBank(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<ApiResponse<Any>>

    @PUT("coupon/applyCoupon/{code}")
    fun addCoupon(
        @Path("code") code: String?,
        @Header("Authorization") auth: String,
    ): Call<CouponData>

    @GET(GET_CALL_HISTORY)
    fun getCallHistory(
        @Query("id") id : String,
        @Header("Authorization") auth: String,
    ):Call<CallHistoryData>

    @PUT("appointment/deleteCallHistory/{id}")
    fun deleteCallHistory(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<ApiResponse<Any>>

    @PUT(BLOCK_USER)
    @FormUrlEncoded
    fun blockUser(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ):Call<ApiResponse<Any>>

    @GET("notification/getNotification/{id}")
    fun getNotifications(
        @Path("id") id: String?,
        @Header("Authorization") auth: String
    ): Call<NotificationData>

    @GET(GET_REPORTABUSE_TYPE)
    fun getType(
        @Header("Authorization") auth: String,
    ): Call<ReportAbuseData>

    @POST(ADD_FEEDBACK)
    @FormUrlEncoded
    fun addFeedback(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<AddReport>


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
    suspend fun getAllAdmin(): Response<AdminResponse>

    @GET(ALL_INFLUENCE + "?interest={id}")
    suspend fun getAdminByInterest(
        @Path("id") id: String
    ): Response<AdminResponse>

    @GET(GET_ADMIN + "/{id}")
    suspend fun getAdminByID(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): Response<AdminResponse1>

    @POST(ADD_BOOKMARK)
    suspend fun addFavourite(
        @Body id: HashMap<String, String>,
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

    @PUT(UPDATE_ADMIN + "/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body data: HashMap<String, Any>,
        @Header("Authorization") authHeader: String
    ): Response<AdminResponse1>

}