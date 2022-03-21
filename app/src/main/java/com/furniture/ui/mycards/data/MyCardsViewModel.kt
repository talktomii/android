package com.furniture.ui.mycards.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.furniture.R
import com.furniture.adapter.MyCardAdapter
import com.furniture.data.apis.WebService
import com.furniture.data.model.CardData
import com.furniture.data.model.UserData
import com.furniture.data.network.responseUtil.ApiResponse
import com.furniture.data.network.responseUtil.ApiUtils
import com.furniture.data.network.responseUtil.Resource
import com.furniture.di.SingleLiveEvent
import com.furniture.ui.mycards.CardItemsViewModel
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

class MyCardsViewModel @Inject constructor(private val webService: WebService) : ViewModel() {

    val addCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val cards by lazy { SingleLiveEvent<Resource<getAllData>>() }
    private var context: Context? = null

    fun ViewModelFactory(context: Context?) {
        this.context = context
    }

    fun getCards() {
        cards.value = Resource.loading()
        Log.d("heyyyyyyyyy", "sbaddbsdhbhb")
        webService.getCards().enqueue(object : Callback<ApiResponse<getAllData>> {
            override fun onResponse(
                call: Call<ApiResponse<getAllData>>,
                response: Response<ApiResponse<getAllData>>
            ) {
                Timber.d("--%s", response.body().toString())
                val dataList = ArrayList<CardItemsViewModel>()
                if (response.isSuccessful) {
                    cards.value = Resource.success(response.body()!!.payload)
                    val data = response.body()
                    var str : String = ""
                    for (i in data!!.payload!!.card!!) {
                        val str = i.cardNumber
                        val sb = StringBuilder("*".repeat(str.length))

                        for (i in 0 until str.length) {
                            if (i >= str.length - 4) {
                                sb.replace(i, i + 1, str[i].toString())

                            }
                        }

                        dataList.add(
                            CardItemsViewModel(
                                maskString(i.cardNumber, 2, 14, '*').toString(),
                                R.drawable.master_card1
                            )
                        )
                        Log.d("datalist---", dataList.toString())
                    }

                    val layoutManager = FlexboxLayoutManager()
                    layoutManager.flexWrap = FlexWrap.WRAP
                    layoutManager.flexDirection = FlexDirection.ROW
                    CardFragment.recycleview.layoutManager = layoutManager
                    val adapter = MyCardAdapter(dataList)
                    CardFragment.recycleview.adapter = adapter

                } else {
                    cards.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                    Timber.d("00000" + cards.value!!.message)
                }
            }

            override fun onFailure(call: Call<ApiResponse<getAllData>>, t: Throwable) {
                cards.value = Resource.error(ApiUtils.failure(t))

            }

        })
    }

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
        webService.addCard(hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200) {
                            Log.d("Response ------", response.body()!!.data.toString())
//                            Toast.makeText(,"added successfully",Toast.LENGTH_SHORT).show()
                            addCard.value = Resource.success(response.body()?.detail)
                        } else addCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        addCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    addCard.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

}