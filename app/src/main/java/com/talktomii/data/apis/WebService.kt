package com.talktomii.data.apis


import com.example.example.PayloadCards
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.ui.banksettings.models.BankData
import com.talktomii.ui.banksettings.models.addBankData
import com.talktomii.ui.mycards.data.addCardData
import com.talktomii.ui.mycards.model.PaymentPayload
import com.talktomii.ui.mywallet.models.CurrentWalletPaylod
import com.talktomii.ui.mywallet.models.WalletPayload
import com.talktomii.ui.mywallet.models.addWalletData
import retrofit2.Call
import retrofit2.http.*


interface WebService {
    companion object {
        private const val ADD_CARD = "card/addCard"
        private const val DELETE_CARD_ITEM = "card/deleteCard/"
        private const val GET_CARDS = "card/getCardByuid/623d9832f521ab28b8f8373a"
        private const val ADD_WALLET = "walletHistory/addWalletHistory"
        private const val GET_WALLET =
            "walletHistory/getWalletHistoryByuid/623d9832f521ab28b8f8373a"
        private const val GET_PAYMENT =
            "walletHistory/getWalletHistoryByuid/623d9832f521ab28b8f8373a"
        private const val GET_CURRENT_AMOUNT =
            "walletHistory/getCurrentAmount/623d9832f521ab28b8f8373a"
        private const val ADD_BANK = "bank/addBank"
        private const val GET_BANK = "bank/getBankByuid/622877f9e3e5080bdcde6ebf"
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

    @POST(ADD_WALLET)
    @FormUrlEncoded
    fun addWallet(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addWalletData>>

    @GET(GET_WALLET)
    fun getWallet(
        @Header("Authorization") auth: String
    ): Call<WalletPayload>

    @GET(GET_CURRENT_AMOUNT)
    fun getCurrentAmount(
        @Header("Authorization") auth: String
    ): Call<CurrentWalletPaylod>

    @GET(GET_PAYMENT)
    fun getPayment(
        @Header("Authorization") auth: String
    ): Call<PaymentPayload>

    @POST(ADD_BANK)
    @FormUrlEncoded
    fun addBank(
        @Header("Authorization") auth: String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addBankData>>

    @GET(GET_BANK)
    fun getBank(
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
}