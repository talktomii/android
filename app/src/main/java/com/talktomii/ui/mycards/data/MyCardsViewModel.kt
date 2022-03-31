package com.talktomii.ui.mycards.data

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.example.PayloadCards
import com.talktomii.R
import com.talktomii.adapter.MyCardAdapter
import com.talktomii.data.apis.WebService
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.di.SingleLiveEvent
import com.talktomii.ui.mycards.activities.MyCardsActivity
import com.talktomii.ui.mycards.fragments.CardFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.apache.commons.lang3.StringUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import com.google.android.material.snackbar.Snackbar
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import com.talktomii.ui.mywallet.models.addWalletData
import org.json.JSONObject
import android.widget.Toast
import com.facebook.FacebookSdk.getApplicationContext
import com.talktomii.adapter.MyPaymentAdapter
import com.talktomii.ui.mywallet.MyWallet
import com.talktomii.ui.mywallet.adapters.WalletRefillAdapter
import com.talktomii.ui.mywallet.fragments.RefillFragment
import com.talktomii.ui.mywallet.models.CurrentWalletPaylod
import com.talktomii.ui.mywallet.models.WalletPayload
import com.talktomii.ui.mywallet.models.WalletRefillItemModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.ui.mycards.PaymentItemsViewModel
import com.talktomii.ui.mycards.fragments.PaymentFragment
import com.talktomii.ui.mycards.model.PaymentPayload
import java.text.ParseException


class MyCardsViewModel @Inject constructor(val webService: WebService) : ViewModel() {

    val addCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val addWallet by lazy { SingleLiveEvent<Resource<Any>>() }
    val cards by lazy { SingleLiveEvent<Resource<PayloadCards>>() }
    val wallets by lazy { SingleLiveEvent<Resource<WalletPayload>>() }
    val updateCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val deleteCard by lazy { SingleLiveEvent<Resource<Any>>() }
    private var context: Context? = null
    val payments by lazy { SingleLiveEvent<Resource<WalletPayload>>() }

    fun ViewModelFactory(context: Context?) {
        this.context = context
    }

    companion object {
        var arrayStrings: ArrayList<String>? = null
        var carditemMap: HashMap<String, String>? = null
        var selectedCardItem: String = ""
    }

    fun getCards() {
        cards.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        webService.getCards("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<PayloadCards> {
                override fun onResponse(
                    call: Call<PayloadCards>,
                    response: Response<PayloadCards>
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
                        val adapter = MyCardAdapter(dataList, webService)
                        CardFragment.recycleview.adapter = adapter
                        CardFragment.progress.visibility = View.GONE
                        CardFragment.recycleview.visibility = View.VISIBLE

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

                override fun onFailure(call: Call<PayloadCards>, t: Throwable) {
                    cards.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getCardlistWallet() {
        cards.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        webService.getCards("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<PayloadCards> {
                override fun onResponse(
                    call: Call<PayloadCards>,
                    response: Response<PayloadCards>
                ) {
                    Timber.d("--%s", response.body().toString())
                    val dataList = ArrayList<CardItemsViewModel>()
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        arrayStrings = ArrayList<String>()
                        carditemMap = HashMap()
                        for (i in data!!.card!!.data) {
                            val refilldata = "****" + i.card!!.last4!!.toString()
                            if (arrayStrings!!.contains(refilldata)) {

                            } else {
                                arrayStrings!!.add(refilldata)
                                carditemMap!!.put(i.id.toString(), refilldata)
                            }


                        }
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            RefillWalletActivity.context,
                            android.R.layout.simple_spinner_dropdown_item,
                            arrayStrings!!.toMutableList()
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        RefillWalletActivity.filterTypes!!.setAdapter(adapter)
                        RefillWalletActivity.filterTypes!!.setOnItemClickListener(object :
                            AdapterView.OnItemClickListener {
                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                Log.d("selected_item", carditemMap!!.keys.toString())
                                for (entry in carditemMap!!.entries) {
                                    if (entry.value === adapter.getItem(p2)) {
                                        System.out.println(
                                            "The key for value " + adapter.getItem(p2)
                                                .toString() + " is " + entry.key
                                        )
                                        selectedCardItem = entry.key
                                        break
                                    }
                                }
                            }

                        })

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

                override fun onFailure(call: Call<PayloadCards>, t: Throwable) {
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
                            MyCardsActivity.finishFunction()
                            getCards()
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

    fun deleteCard(id: String) {
        deleteCard.value = Resource.loading()
        webService.deleteCard(id, "Bearer " + MainActivity.retrivedToken)
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

    fun addWallet(hashMap: HashMap<String, String>) {
        addWallet.value = Resource.loading()
        webService.addWallet("Bearer " + MainActivity.retrivedToken, hashMap)
            .enqueue(object : Callback<ApiResponse<addWalletData>> {
                override fun onResponse(
                    call: Call<ApiResponse<addWalletData>>,
                    response: Response<ApiResponse<addWalletData>>
                ) {
                    if (response.isSuccessful) {
                        val snackbar = Snackbar.make(
                            RefillWalletActivity.refillLayout!!,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        RefillWalletActivity.etAmount!!.text.clear()
                        RefillWalletActivity.walletProgress!!.visibility = View.GONE
                        if (RefillWalletActivity.repeatAmount != null) {
                            RefillWalletActivity.finishFunction()
                            getPayment()
                        } else {
                            getWallet()
                            getTotalAmount()
                            getCurrentAmount()
                        }

                    } else {
                        addWallet.value = Resource.error(
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
                                RefillWalletActivity.refillLayout!!,
                                "Something wrong",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: Exception) {
                            val snackbar = Snackbar.make(
                                RefillWalletActivity.refillLayout!!,
                                "Something wrong",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }

                        RefillWalletActivity.walletProgress!!.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<ApiResponse<addWalletData>>, t: Throwable) {
                    addWallet.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getWallet() {
        wallets.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        val dataList = ArrayList<WalletRefillItemModel>()
        webService.getWallet("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<WalletPayload> {
                override fun onResponse(
                    call: Call<WalletPayload>,
                    response: Response<WalletPayload>
                ) {
                    Timber.d("--%s", response.body().toString())
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        MyWallet.totalWalletAmount!!.text = "$" + data!!.currentAmount
                        for (i in data.wallet) {
                            val sdf1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH)
                            val sdf2 = SimpleDateFormat("dd.mm.yyyy hh:mm a", Locale.ENGLISH)
                            var date: Date? = null
                            try {
                                date = sdf1.parse(i.createdAt)
                                val newDate = sdf2.format(date)
                                println(newDate)
                                Log.e("Date", newDate)
                                dataList.add(
                                    WalletRefillItemModel(
                                        wallet_name = i.type!!,
                                        wallet_date = newDate,
                                        wallet_price = "-$" + i.amount
                                    )
                                )
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }
                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        RefillFragment.recyclerview!!.layoutManager = layoutManager
                        val adapter = WalletRefillAdapter(dataList)
                        RefillFragment.recyclerview!!.adapter = adapter
                        RefillFragment.progress!!.visibility = View.GONE
                        RefillFragment.recyclerview!!.visibility = View.VISIBLE
                    } else {
                        Log.d("card data is : ", " : " + response.body())
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value!!.message)
                    }
                }

                override fun onFailure(call: Call<WalletPayload>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getCurrentAmount() {
        wallets.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        val dataList = ArrayList<WalletRefillItemModel>()
        webService.getWallet("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<WalletPayload> {
                override fun onResponse(
                    call: Call<WalletPayload>,
                    response: Response<WalletPayload>
                ) {
                    Timber.d("--%s", response.body().toString())
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        MyWallet.totalWalletAmount!!.text = "$" + data!!.currentAmount
                    } else {
                        Log.d("card data is : ", " : " + response.body())
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value!!.message)
                    }
                }

                override fun onFailure(call: Call<WalletPayload>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getTotalAmount() {
        wallets.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        val dataList = ArrayList<WalletRefillItemModel>()
        webService.getCurrentAmount("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<CurrentWalletPaylod> {
                override fun onResponse(
                    call: Call<CurrentWalletPaylod>,
                    response: Response<CurrentWalletPaylod>
                ) {
                    Timber.d("--%s", response.body().toString())
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        MainActivity.totalSideBarAmount!!.text =
                            "$" + data!!.walletData!!.currentAmount
                    } else {
                        Log.d("card data is : ", " : " + response.body())
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value!!.message)
                    }
                }

                override fun onFailure(call: Call<CurrentWalletPaylod>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getPayment() {
        payments.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        val dataList = ArrayList<PaymentItemsViewModel>()
        webService.getPayment("Bearer " + MainActivity.retrivedToken)
            .enqueue(object : Callback<PaymentPayload> {
                override fun onResponse(
                    call: Call<PaymentPayload>,
                    response: Response<PaymentPayload>
                ) {
                    Timber.d("--%s", response.body().toString())
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        for (i in data!!.wallet) {
                            val sdf1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH)
                            val sdf2 = SimpleDateFormat("dd.mm.yyyy hh:mm a", Locale.ENGLISH)
                            var date: Date? = null
                            try {
                                date = sdf1.parse(i.createdAt)
                                val newDate = sdf2.format(date)
                                println(newDate)
                                Log.e("Date", i.toString())
                                var paymentId = ""
                                if(i.paymentId != null){
                                    paymentId = i.paymentId!!
                                }
                                dataList.add(
                                    PaymentItemsViewModel(
                                        wallet_id = paymentId,
                                        wallet_name = "Wallet " + i.type!!,
                                        wallet_date = newDate,
                                        wallet_price = "-$" + i.amount
                                    )
                                )
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }
                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        PaymentFragment.recycleview.layoutManager = layoutManager
                        val adapter = MyPaymentAdapter(dataList)
                        PaymentFragment.recycleview.adapter = adapter
                        PaymentFragment.progress.visibility = View.GONE
                        PaymentFragment.recycleview.visibility = View.VISIBLE
                    } else {
                        Log.d("card data is : ", " : " + response.body())
                        payments.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value!!.message)
                    }
                }

                override fun onFailure(call: Call<PaymentPayload>, t: Throwable) {
                    payments.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }
}
