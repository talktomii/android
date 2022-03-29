package com.furniture.ui.mycards.data

import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.example.ExampleJson2KtKotlin
import com.furniture.R
import com.furniture.adapter.MyCardAdapter
import com.furniture.data.apis.WebService
import com.furniture.data.model.CardData
import com.furniture.data.model.UserData
import com.furniture.data.network.Config
import com.furniture.data.network.responseUtil.ApiResponse
import com.furniture.data.network.responseUtil.ApiUtils
import com.furniture.data.network.responseUtil.Resource
import com.furniture.di.SingleLiveEvent
import com.furniture.ui.loginSignUp.MainActivity
import com.furniture.ui.mycards.activities.MyCardsActivity
import com.furniture.ui.mycards.fragments.CardFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.apache.commons.lang3.StringUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import java.lang.StringBuilder
import javax.inject.Inject
import com.google.android.material.snackbar.Snackbar
import com.google.api.client.json.Json
import org.json.JSONObject

import retrofit2.adapter.rxjava2.Result.response


class MyCardsViewModel @Inject constructor(val webService: WebService) : ViewModel() {

    val addCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val cards by lazy { SingleLiveEvent<Resource<ExampleJson2KtKotlin>>() }
    val updateCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val deleteCard by lazy { SingleLiveEvent<Resource<Any>>() }
    private var context: Context? = null

    fun ViewModelFactory(context: Context?) {
        this.context = context
    }

    companion object{

    }

    fun getCards() {
        cards.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        webService.getCards("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<ExampleJson2KtKotlin> {
                override fun onResponse(
                    call: Call<ExampleJson2KtKotlin>,
                    response: Response<ExampleJson2KtKotlin>
                ) {
                    Timber.d("--%s", response.body().toString())
                    val dataList = ArrayList<CardItemsViewModel>()
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        for (i in data!!.card!!.data) {
                            Log.d("mydata0", i.card!!.last4.toString())
                            dataList.add(
                                CardItemsViewModel(
                                    i.id!!,
                                    R.drawable.master_card1,
                                    "****" + i.card!!.last4.toString()
                                )
                            )
                        }


                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        CardFragment.recycleview.layoutManager = layoutManager
                        val adapter = MyCardAdapter(dataList)
                        CardFragment.recycleview.adapter = adapter

                    } else {
                        Log.d("card data is : ", " : " + response.body())
                        cards.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value!!.message)
                    }
                }

                override fun onFailure(call: Call<ExampleJson2KtKotlin>, t: Throwable) {
                    cards.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    //    maskString(i.cardNumber, 2, 14, '*').toString(),
    fun changeCharInPosition(position: Int, ch: Char, str: String): String? {
        val charArray = str.toCharArray()
        charArray[position] = ch
        return String(charArray)
    }

    @Throws(Exception::class)
    private fun maskString(strText: String?, start: Int, end: Int, maskChar: Char): String? {
        var start = start
        var end = end
        if (strText == null || strText == "") return ""
        if (start < 0) start = 0
        if (end > strText.length) end = strText.length
        if (start > end) throw Exception("End index cannot be greater than start index")
        val maskLength = end - start
        if (maskLength == 0) return strText
        val strMaskString = StringUtils.repeat(maskChar, maskLength)
        return StringUtils.overlay(strText, strMaskString, start, end)
    }

    fun addCard(hashMap: HashMap<String, String>) {
        addCard.value = Resource.loading()
        webService.addCard("Bearer " + MainActivity.retrivedToken, hashMap)
            .enqueue(object : Callback<ApiResponse<addCardData>> {
                override fun onResponse(
                    call: Call<ApiResponse<addCardData>>,
                    response: Response<ApiResponse<addCardData>>
                ) {
                    if (response.isSuccessful) {
                        val snackbar = Snackbar.make(
                            MyCardsActivity.layout,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        if (response.body()!!.message == "added successfully") {
                            MyCardsActivity.progress.visibility = View.GONE
                            MyCardsActivity.finish()
                        }
                    } else {
                        addCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        val data = response.errorBody()!!.string()
                        Log.d("errordata", response.toString())
                        try {
                            val jObjError = JSONObject(data)

                            val snackbar = Snackbar.make(
                                MyCardsActivity.layout,
                                "Something wrong",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: Exception) {
                            val snackbar = Snackbar.make(
                                MyCardsActivity.layout,
                                "Something wrong",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }

                        MyCardsActivity.progress.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<ApiResponse<addCardData>>, t: Throwable) {
                    addCard.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun updateCard(hashMap: HashMap<String, Any>) {
        updateCard.value = Resource.loading()
        Log.d("updated ::: ", hashMap.toString())
        webService.updateCard("62382e455ccbb6952f15f25d", hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {

                    Log.d("call --- > ", call.toString())
                    if (response.isSuccessful) {
                        Log.d("success --->  ", response.body().toString())
                        if (response.body()?.status == 200) {
                            Log.d("Response ------", response.body()!!.data.toString())
//                            Toast.makeText(,"added successfully",Toast.LENGTH_SHORT).show()
                            updateCard.value = Resource.success(response.body()?.detail)
                        } else updateCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        updateCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    updateCard.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun deleteCard(id : String) {
        deleteCard.value = Resource.loading()
        webService.deleteCard(id,"Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200) {
                            Log.d("Response ------", response.body()!!.data.toString())
//                            Toast.makeText(,"added successfully",Toast.LENGTH_SHORT).show()
                            deleteCard.value = Resource.success(response.body()?.detail)
                        } else deleteCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        deleteCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    deleteCard.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

}