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
import retrofit2.http.*


interface WebService {
    companion object {
        private const val ADD_CARD = "card/addCard"
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
}