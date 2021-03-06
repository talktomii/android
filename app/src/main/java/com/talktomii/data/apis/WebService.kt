package com.talktomii.data.apis

import com.talktomii.data.model.AllInterst
import com.talktomii.data.model.InterestResponse
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.model.RolesModel
import com.talktomii.data.model.addbookmark.AddBookMarkResponse
import com.talktomii.data.model.admin.AdminResponse
import com.talktomii.data.model.admin1.AdminResponse1
import com.talktomii.data.model.admin1.ResponseInfluencerDashboard
import com.talktomii.data.model.admin1.UpdateAdmin
import com.talktomii.data.model.admin1.UpdateAvailability
import com.talktomii.data.model.appointment.GetAllCalenderAppoinment
import com.talktomii.data.model.appointment.GetAppointmentById
import com.talktomii.data.model.appointment.UpdateAppointment
import com.talktomii.data.model.currentwallet.CurrentWallet
import com.talktomii.data.model.drawer.bookmark.BookMarkResponse
import com.talktomii.data.model.getallslotbydate.GetAllAppoinments
import com.talktomii.data.model.getallslotbydate.GetAllSlotByDateResponse
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.photo.UpdatePhoto
import com.talktomii.ui.banksettings.models.BankData
import com.talktomii.ui.banksettings.models.addBankData
import com.talktomii.ui.callhistory.models.CallHistoryData
import com.talktomii.ui.coupon.models.CouponData
import com.talktomii.ui.home.notifications.models.NotificationData
import com.talktomii.ui.mycards.data.addCardData
import com.talktomii.ui.mycards.model.PayloadCards
import com.talktomii.ui.mycards.model.PaymentPayload
import com.talktomii.ui.mywallet.models.CurrentWalletPaylod
import com.talktomii.ui.mywallet.models.WalletPayload
import com.talktomii.ui.mywallet.models.addWalletData
import com.talktomii.ui.reportabuse.models.AddReport
import com.talktomii.ui.reportabuse.models.ReportAbuseData
import com.talktomii.ui.tellusmore.RequestAdminModel
import okhttp3.RequestBody
import retrofit2.Call
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
        private const val WITHDRAW_AMOUNT = "withdraw/addWithdraw"

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
        private const val UPDATE_AVAIBILITY = "admin/updateAvailability"
        private const val DELETE_AVAIBILITY = "admin/deleteAvailability"
        private const val VERIFY_EMAIL = "admin/verify-email"
        private const val VERIFY_CODE = "admin/verify-code"
        private const val AFTER_FORGET = "admin/after-forget"
        private const val GET_CURRENT_AMOUNT = "walletHistory/getCurrentAmount"
        private const val GET_ALL_APPOINTMENT = "appointment/getAllAppointment"
        private const val UPDATE_APPOINTMENT = "appointment/updateAppointment"
        private const val GET_APPOINTMENT_BY_DATE = "appointment/getAppointmentByDate"
        private const val GET_APPOINTMENT_BY_ID = "appointment/getAppointmentById"
        private const val GET_INFLUENCER_DASHBOARD = "admin/influencer-dashboard"
        private const val UPDATE_VIDEO = "admin/updateAudio"
        private const val UPLOAD_MEDIA = "admin/updateAudio/{id}"
       private const val CHECKUSER_NAME = "admin/checkuserName"
    }

    @GET("card/getCardByuid/{id}")
    fun getCards(
        @Path("id") id: String?
    ): Call<PayloadCards>

    @GET("admin/agora")
    fun initCall(
        @Query("channelName") channelName: String?
    ): Call<ApiResponse<RegisterModel>>

    @GET(CHECKUSER_NAME)
    fun checkUserName(
        @Query("userName") userName: String?
    ): Call<ApiResponse<Any>>

    @POST(ADD_CARD)
    @FormUrlEncoded
    fun addCard(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addCardData>>

    @DELETE("card/deleteCard/{id}")
    fun deleteCard(
        @Path("id") id: String?
    ): Call<ApiResponse<Any>>

    @POST(ADD_WALLET)
    @FormUrlEncoded
    fun addWallet(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addWalletData>>

    @GET("walletHistory/getWalletHistoryByuid/{id}")
    fun getWallet(
        @Path("id") id: String?
    ): Call<WalletPayload>

    @Multipart
    @POST(REGISTER)
    fun createProfile(
        @PartMap map: HashMap<String, RequestBody>
    ): Call<ApiResponse<RegisterModel>>

    @Multipart
    @PUT(UPLOAD_MEDIA)
    fun uploadMedia(
        @PartMap map: HashMap<String, RequestBody>,
        @Path("id") uId:String
    ): Call<ApiResponse<Any>>


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
        @Path("id") id: String?
    ): Call<CurrentWalletPaylod>

    @GET("walletHistory/getWalletHistoryByuid/{id}")
    fun getPayment(
        @Path("id") id: String?
    ): Call<PaymentPayload>

    @POST(ADD_BANK)
    @FormUrlEncoded
    fun addBank(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addBankData>>

    @GET("bank/getBankByuid/{id}")
    fun getBank(
        @Path("id") id: String?
    ): Call<BankData>

    @PUT("bank/updateBank/{id}")
    @FormUrlEncoded
    fun updateBank(
        @Path("id") id: String?,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<Any>>

    @DELETE("bank/deleteBank/{id}")
    fun deleteBank(
        @Path("id") id: String?
    ): Call<ApiResponse<Any>>

    @PUT("coupon/applyCoupon/{code}")
    fun addCoupon(
        @Path("code") code: String?,
    ): Call<CouponData>

    @GET(GET_CALL_HISTORY)
    fun getCallHistory(
        @Query("id") id: String
    ): Call<CallHistoryData>

    @PUT("appointment/deleteCallHistory/{id}")
    fun deleteCallHistory(
        @Path("id") id: String?,
    ): Call<ApiResponse<Any>>

    @PUT(BLOCK_USER)
    @FormUrlEncoded
    fun blockUser(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<Any>>

    @GET("notification/getNotification/{id}")
    fun getNotifications(
        @Path("id") id: String?
    ): Call<NotificationData>

    @GET(GET_REPORTABUSE_TYPE)
    fun getType(
    ): Call<ReportAbuseData>

    @POST(ADD_FEEDBACK)
    @FormUrlEncoded
    fun addFeedback(
        @FieldMap map: HashMap<String, String>
    ): Call<AddReport>

    @GET(GET_CALL_HISTORY)
    fun getSearchedCallHistory(
        @Query("id") id: String,
        @Query("search") search: String
    ): Call<CallHistoryData>

    @POST(WITHDRAW_AMOUNT)
    @FormUrlEncoded
    fun getPaid(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<Any>>

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
    fun getInterests(): Call<ApiResponse<InterestResponse>>

    @GET(ALL_INTERST)
    suspend fun getSearchInterest(@Query("search") search: String): Response<AllInterst>

    @GET(ALL_INFLUENCE)
    suspend fun getAllAdmin(): Response<AdminResponse>

    @GET(ALL_INFLUENCE)
    suspend fun getAdminByInterest(
        @Query("interest") id: String
    ): Response<AdminResponse>

    @GET(GET_ADMIN + "/{id}")
    suspend fun getAdminByID(
        @Path("id") id: String,
    ): Response<AdminResponse1>

    @POST(ADD_BOOKMARK)
    suspend fun addFavourite(
        @Body id: HashMap<String, Any>,
    ): Response<AddBookMarkResponse>

    @DELETE(REMOVE_BOOKMARK + "/{id}")
    suspend fun removeBookmark(
        @Path("id") id: String
    ): Response<AddBookMarkResponse>

    @GET(GET_BOOKMARKS)
    suspend fun getBookmarks(
        @Query("uid") id: String,
    ): Response<BookMarkResponse>

    @POST(ADD_APPOINMENT)
    suspend fun addAppoinment(
        @Body id: HashMap<String, Any>,
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

        ): Response<UpdatePhoto>

    @PUT(UPDATE_ADMIN + "/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body data: HashMap<String, Any>,
    ): Response<UpdateAdmin>

    @PUT(UPDATE_AVAIBILITY)
    suspend fun updateAvailability(
        @Body data: HashMap<String, Any>,
    ): Response<UpdateAvailability>


    @DELETE(DELETE_AVAIBILITY)
    suspend fun deleteAvailability(
        @Query("id") id: String,
        @Query("uid") uid: String,
    ): Response<UpdateAvailability>


    @GET(GET_CURRENT_AMOUNT + "/{id}")
    suspend fun getCurrentAmountFromWallet(
        @Query("id") id: String
    ): Response<CurrentWallet>


    @GET(GET_ALL_APPOINTMENT)
    suspend fun getAllAppointment(
        @Query("ifid") id: String
    ): Response<GetAllAppoinments>

    @GET(GET_ALL_APPOINTMENT)
    suspend fun getCalenderAllAppointment(
        @Query("ifid") id: String
    ): Response<GetAllCalenderAppoinment>

    @GET(GET_APPOINTMENT_BY_ID + "/{id}")
    suspend fun getAppointmentById(
        @Path("id") id: String
    ): Response<GetAppointmentById>
//        @Body data: HashMap<String, Any>
//    ): Response<AdminResponse1>

    @PUT(VERIFY_EMAIL)
    fun verifyEmail(
        @Body data: HashMap<String, String>
    ): Call<ApiResponse<Any>>

    @PUT(VERIFY_CODE)
    fun verifyCode(
        @Body data: HashMap<String, String>
    ): Call<ApiResponse<Any>>

    @FormUrlEncoded
    @POST(AFTER_FORGET)
    fun afterForget(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<Any>>


    @PUT(UPDATE_ADMIN + "/{id}")
    fun updateData(
        @Path("id") id: String,
        @Body data: RequestAdminModel
    ): Call<ApiResponse<Any>>


    @GET(GET_APPOINTMENT_BY_DATE)
    suspend fun getAppointmentByDate(
        @Query("date") date: String,
        @Query("id") id: String
    ): Response<GetAllCalenderAppoinment>

    @GET(GET_APPOINTMENT_BY_DATE)
    suspend fun getAppointmentByDateWithStatus(
        @Query("date") date: String,
        @Query("id") id: String,
        @Query("status") status: String,
    ): Response<GetAllCalenderAppoinment>

    @PUT(UPDATE_APPOINTMENT + "/{id}")
    suspend fun updateAppointment(
        @Path("id") id: String,
        @Body data: HashMap<String, Any>,
    ): Response<UpdateAppointment>

    @GET(GET_CALL_HISTORY)
    suspend fun getCallUsersCallHistory(
        @Query("id") id: String
    ): Response<CallHistoryData>

    @PUT(UPDATE_APPOINTMENT + "/{id}")
    suspend fun deleteAppointment(
        @Path("id") id: String,
        @Body data: HashMap<String, Any>,
    ): Response<UpdateAppointment>


    @GET(GET_INFLUENCER_DASHBOARD)
    suspend fun getInfluencerDashboard(
        @Query("type") type: String,
        @Query("id") id: String
    ): Response<ResponseInfluencerDashboard>

    @GET(ALL_INFLUENCE)
    suspend fun getAdminsBySearch(
        @Query("search") id: String
    ): Response<AdminResponse>

    @Multipart
    @PUT(UPDATE_VIDEO + "/{id}")
    suspend fun updateAboutYou(
        @Path("id") id: String,
        @PartMap map: HashMap<String, RequestBody>
    ): Response<UpdatePhoto>


}