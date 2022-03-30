package com.talktomii.data.apis


import com.example.example.PayloadCards
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.ui.mycards.data.addCardData
import retrofit2.Call
import retrofit2.http.*


interface WebService {
    companion object {
        private const val ADD_CARD = "card/addCard"
        private const val DELETE_CARD_ITEM = "card/deleteCard/"
        private const val GET_CARDS = "card/getCardByuid/623dac1b6ee1ef298842b9ad"
    }

    @GET(GET_CARDS)
    fun getCards(
        @Header("Authorization") auth : String
    ): Call<PayloadCards>

    @POST(ADD_CARD)
    @FormUrlEncoded
    fun addCard(
        @Header("Authorization") auth : String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addCardData>>

    @DELETE("card/deleteCard/{id}")
    fun deleteCard( @Path("id") id: String?,  @Header("Authorization") auth : String): Call<ApiResponse<Any>>



}