package com.talktomii.ui.mycards.data

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.example.PayloadCards
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.talktomii.R
import com.talktomii.adapter.MyBankAdapter
import com.talktomii.adapter.MyCardAdapter
import com.talktomii.adapter.MyPaymentAdapter
import com.talktomii.data.apis.WebService
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.di.SingleLiveEvent
import com.talktomii.ui.banksettings.BankItemModel
import com.talktomii.ui.banksettings.MyBankSettings
import com.talktomii.ui.banksettings.activities.AddBankAccountActivity
import com.talktomii.ui.banksettings.models.BankData
import com.talktomii.ui.banksettings.models.addBankData
import com.talktomii.ui.callhistory.adapters.CallHistoryAdapter
import com.talktomii.ui.callhistory.models.CallHistoryData
import com.talktomii.ui.callhistory.models.CallHistoryItemModel
import com.talktomii.ui.coupon.CouponActivity
import com.talktomii.ui.coupon.models.CouponData
import com.talktomii.ui.home.notifications.AdapterTodayNotification
import com.talktomii.ui.home.notifications.NotificationFragment
import com.talktomii.ui.home.notifications.NotificationItemModel
import com.talktomii.ui.home.notifications.models.NotificationData
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.ui.mycards.PaymentItemsViewModel
import com.talktomii.ui.mycards.activities.MyCardsActivity
import com.talktomii.ui.mycards.fragments.CardFragment
import com.talktomii.ui.mycards.fragments.PaymentFragment
import com.talktomii.ui.mycards.model.PaymentPayload
import com.talktomii.ui.mywallet.MyWallet
import com.talktomii.ui.mywallet.activities.GetPaidActivity
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import com.talktomii.ui.mywallet.adapters.WalletEarningAdapter
import com.talktomii.ui.mywallet.adapters.WalletExpensesAdapter
import com.talktomii.ui.mywallet.adapters.WalletRefillAdapter
import com.talktomii.ui.mywallet.fragments.EarningFragment
import com.talktomii.ui.mywallet.fragments.ExpenseFragment
import com.talktomii.ui.mywallet.fragments.RefillFragment
import com.talktomii.ui.mywallet.models.*
import com.talktomii.ui.reportabuse.ReportAbuseActivity
import com.talktomii.ui.reportabuse.models.AddReport
import com.talktomii.ui.reportabuse.models.ReportAbuseData
import com.talktomii.utlis.DateUtils.getStringToDateWithDots
import com.talktomii.utlis.PrefsManager
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MyCardsViewModel @Inject constructor(val webService: WebService) : ViewModel() {

    val addCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val addWallet by lazy { SingleLiveEvent<Resource<Any>>() }
    val cards by lazy { SingleLiveEvent<Resource<PayloadCards>>() }
    val type by lazy { SingleLiveEvent<Resource<ReportAbuseData>>() }
    val notification by lazy { SingleLiveEvent<Resource<NotificationData>>() }
    val wallets by lazy { SingleLiveEvent<Resource<WalletPayload>>() }
    val updateCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val deleteCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val deleteBank by lazy { SingleLiveEvent<Resource<Any>>() }
    val deleteCallHistory by lazy { SingleLiveEvent<Resource<Any>>() }
    val addFeedback by lazy { SingleLiveEvent<Resource<AddReport>>() }
    private var context: Context? = null
    val payments by lazy { SingleLiveEvent<Resource<WalletPayload>>() }
    val banks by lazy { SingleLiveEvent<Resource<BankData>>() }

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS

    @Inject
    lateinit var prefsManager: PrefsManager


    private fun currentDate(): Date? {
        val calendar = Calendar.getInstance()
        return calendar.time
    }


    fun ViewModelFactory(context: Context?) {
        this.context = context
    }

    companion object {
        var arrayStrings: ArrayList<String>? = null
        var carditemMap: HashMap<String, String>? = null
        var type_item: HashMap<String, String>? = null
        var selectedCardItem: String = ""
        var selectedType: String = ""
    }

    fun getCards() {
        cards.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        webService.getCards(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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

                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            val userMessage = jsonObject.getString("message")
                            val snackbar = Snackbar.make(
                                CardFragment.layout,
                                userMessage,
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                            CardFragment.progress.visibility = View.GONE
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        cards.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )

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
        arrayStrings = ArrayList<String>()
        arrayStrings!!.add("Select card")
        webService.getCards(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                        RefillWalletActivity.filterTypes!!.adapter = adapter
                        RefillWalletActivity.filterTypes!!.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
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

                                override fun onNothingSelected(p0: AdapterView<*>?) {

                                }
                            }

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
//        addCard.value = Resource.loading()
        webService.addCard(hashMap)
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
//                        addCard.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.errorBody()?.string()
//                            )
//                        )
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            val userMessage = jsonObject.getString("message")
                            val snackbar = Snackbar.make(
                                MyCardsActivity.layout,
                                userMessage,
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
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
        webService.deleteCard(id)
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
        webService.addWallet(hashMap)
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
                            getWallet()
                            getTotalAmount()
                            getCurrentAmount()
                        } else {
                            getPayment()
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
        Log.d("token : ", prefsManager.getString(PrefsManager.PREF_API_TOKEN, ""))
        val dataList = ArrayList<WalletRefillItemModel>()
        webService.getWallet(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                            try {
                                if (i.type!! == "Credit") {
                                    dataList.add(
                                        WalletRefillItemModel(
                                            wallet_name = "Wallet " + i.type!!,
                                            wallet_date = getStringToDateWithDots(i.createdAt!!),
                                            wallet_price = "-$" + i.amount
                                        )
                                    )
                                }
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
                        RefillFragment.progress!!.visibility = View.GONE
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<WalletPayload>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getEarnings() {
        wallets.value = Resource.loading()
        Log.d("token : ", prefsManager.getString(PrefsManager.PREF_API_TOKEN, ""))
        val dataList = ArrayList<WalletEarningItemModel>()
        webService.getWallet(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                            try {
                                if (i.type!! == "Credit") {
                                    dataList.add(
                                        WalletEarningItemModel(
                                            wallet_name = "Wallet " + i.type!!,
                                            wallet_date = getStringToDateWithDots(i.createdAt!!),
                                            wallet_price = "-$" + i.amount
                                        )
                                    )
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }
                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        EarningFragment.recyclerview!!.layoutManager = layoutManager
                        val adapter = WalletEarningAdapter(dataList)
                        EarningFragment.recyclerview!!.adapter = adapter
                        EarningFragment.progress!!.visibility = View.GONE
                        EarningFragment.recyclerview!!.visibility = View.VISIBLE
                    } else {
                        EarningFragment.progress!!.visibility = View.GONE
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<WalletPayload>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getExpenses() {
        wallets.value = Resource.loading()
        Log.d("token : ", prefsManager.getString(PrefsManager.PREF_API_TOKEN, ""))
        val dataList = ArrayList<WalletExpensesItemModel>()
        webService.getWallet(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                            try {
                                if (i.type!! == "Debit") {
                                    dataList.add(
                                        WalletExpensesItemModel(
                                            wallet_name = "Wallet " + i.type!!,
                                            wallet_date = getStringToDateWithDots(i.createdAt!!),
                                            wallet_price = "-$" + i.amount
                                        )
                                    )
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }
                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        ExpenseFragment.recyclerview!!.layoutManager = layoutManager
                        val adapter = WalletExpensesAdapter(dataList)
                        ExpenseFragment.recyclerview!!.adapter = adapter
                        ExpenseFragment.progress!!.visibility = View.GONE
                        ExpenseFragment.recyclerview!!.visibility = View.VISIBLE
                    } else {
                        ExpenseFragment.progress!!.visibility = View.GONE
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<WalletPayload>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getCurrentWallet() {
        wallets.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        val dataList = ArrayList<WalletRefillItemModel>()
        webService.getCurrentAmount(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
            .enqueue(object : Callback<CurrentWalletPaylod> {
                override fun onResponse(
                    call: Call<CurrentWalletPaylod>,
                    response: Response<CurrentWalletPaylod>
                ) {
                    Timber.d("--%s", response.body().toString())
                    if (response.isSuccessful) {
//                        cards.value = Resource.success(response.body()!!.payload)
                        val data = response.body()!!.payload
                        GetPaidActivity.total.text = "$" + data!!.walletData!!.currentAmount
                    } else {
                        Log.d("card data is : ", " : " + response.body())
                        wallets.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value?.message)
                    }
                }

                override fun onFailure(call: Call<CurrentWalletPaylod>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getCurrentAmount() {
        wallets.value = Resource.loading()
        Log.d("token : ", MainActivity.retrivedToken)
        val dataList = ArrayList<WalletRefillItemModel>()
        webService.getWallet(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                        Timber.d("00000" + cards.value?.message)
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
        webService.getCurrentAmount(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                        Timber.d("00000" + cards.value?.message)
                    }
                }

                override fun onFailure(call: Call<CurrentWalletPaylod>, t: Throwable) {
                    wallets.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getPayment() {
        payments.value = Resource.loading()
        Log.d(
            "token : ",
            prefsManager.getString(PrefsManager.PREF_API_ID, "") + "-- " + prefsManager.getString(
                PrefsManager.PREF_API_TOKEN,
                ""
            )
        )
        val dataList = ArrayList<PaymentItemsViewModel>()
        webService.getPayment(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
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
                            try {
                                var paymentId = ""
                                if (i.paymentId != null) {
                                    paymentId = i.paymentId!!
                                }
                                dataList.add(
                                    PaymentItemsViewModel(
                                        wallet_id = paymentId,
                                        wallet_name = "Wallet " + i.type!!,
                                        wallet_date = getStringToDateWithDots(i.createdAt!!),
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
                        payments.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<PaymentPayload>, t: Throwable) {
                    payments.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun getBank() {
        banks.value = Resource.loading()
        Log.d("token bank: ", MainActivity.retrivedToken)
        val dataList = ArrayList<BankItemModel>()
        webService.getBank(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
            .enqueue(object : Callback<BankData> {
                override fun onResponse(
                    call: Call<BankData>,
                    response: Response<BankData>
                ) {
                    Timber.d("--%s", response.body().toString())
                    if (response.isSuccessful) {
                        Log.d("success here : ", "yesssss")
                        val data = response.body()!!.payload
                        for (i in data!!.BANK) {
                            try {
                                dataList.add(
                                    BankItemModel(
                                        i.Id!!,
                                        i.holderName!!.uppercase(Locale.getDefault()),
                                        i.bankType!!,
                                        i.routingNumber!!,
                                        i.accountNumber!!
                                    )
                                )
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }
                        Log.d("datalist bank data : ", " j " + dataList.toString())
                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        MyBankSettings.recycleview.layoutManager = layoutManager
                        val adapter = MyBankAdapter(dataList, webService)
                        MyBankSettings.recycleview.adapter = adapter
                        MyBankSettings.progress.visibility = View.GONE
                        MyBankSettings.recycleview.visibility = View.VISIBLE
                    } else {
                        banks.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<BankData>, t: Throwable) {
                    Log.d("bank failed : ", " : " + t.message)
                    banks.value = Resource.error(ApiUtils.failure(t))

                }

            })
    }

    fun addBank(hashMap: HashMap<String, String>) {
        addWallet.value = Resource.loading()
        webService.addBank(hashMap)
            .enqueue(object : Callback<ApiResponse<addBankData>> {
                override fun onResponse(
                    call: Call<ApiResponse<addBankData>>,
                    response: Response<ApiResponse<addBankData>>
                ) {
                    if (response.isSuccessful) {
                        AddBankAccountActivity.progress.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            AddBankAccountActivity.layout,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        AddBankAccountActivity.finishFunction()
                        getBank()
                    } else {
                        addWallet.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        var jsonObject: JSONObject? = null
                        AddBankAccountActivity.progress.visibility = View.GONE
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            val userMessage = jsonObject.getString("message")
                            val snackbar = Snackbar.make(
                                AddBankAccountActivity.layout,
                                userMessage,
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                }

                override fun onFailure(call: Call<ApiResponse<addBankData>>, t: Throwable) {
                    addWallet.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun deleteBank(id: String) {
        deleteBank.value = Resource.loading()
        webService.deleteBank(id)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200) {
                            Log.d("Response ------", response.body()!!.data.toString())
//                            Toast.makeText(,"added successfully",Toast.LENGTH_SHORT).show()
                            deleteBank.value = Resource.success(response.body()?.detail)
                        } else deleteBank.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        deleteBank.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    deleteBank.value = Resource.error(ApiUtils.failure(t))
                }
            })
    }

    fun updateBank(id: String, hashMap: HashMap<String, String>) {
        addWallet.value = Resource.loading()
        webService.updateBank(id, hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        val snackbar = Snackbar.make(
                            AddBankAccountActivity.layout,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        AddBankAccountActivity.progress.visibility = View.GONE
                        AddBankAccountActivity.finishFunction()
                        getBank()
                    } else {
                        addWallet.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        val data = response.errorBody()!!.string()
                        try {
                            val jObjError = JSONObject(data)

                            val snackbar = Snackbar.make(
                                AddBankAccountActivity.layout,
                                "Something wrong",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: Exception) {
                            val snackbar = Snackbar.make(
                                AddBankAccountActivity.layout,
                                "Something wrong",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    addWallet.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun addCoupon(name: String) {
        webService.addCoupon(name)
            .enqueue(object : Callback<CouponData> {
                override fun onResponse(
                    call: Call<CouponData>,
                    response: Response<CouponData>
                ) {
                    if (response.isSuccessful) {
                        val snackbar = Snackbar.make(
                            CouponActivity.layout,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        CouponActivity.progress.visibility = View.GONE
                        CouponActivity.finishFunction()
                        getCurrentAmount()
                        getTotalAmount()
                    } else {
                        val snackbar = Snackbar.make(
                            CouponActivity.layout,
                            "Something Wrong",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        CouponActivity.progress.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<CouponData>, t: Throwable) {
                    val snackbar = Snackbar.make(
                        CouponActivity.layout,
                        "Coupon Not Added",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                    CouponActivity.progress.visibility = View.GONE
                }
            })
    }

    fun getCallHistory() {
        webService.getCallHistory(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
            .enqueue(object : Callback<CallHistoryData> {
                override fun onResponse(
                    call: Call<CallHistoryData>,
                    response: Response<CallHistoryData>
                ) {
                    val dataList = ArrayList<CallHistoryItemModel>()
                    if (response.isSuccessful) {
                        val data = response.body()!!.payload
                        for (i in data!!.callHistory) {
                            try {
                                if(prefsManager.getString(PrefsManager.PREF_ROLE,"") == "user"){
                                    dataList.clear()
                                    dataList.add(
                                        CallHistoryItemModel(
                                            i.ifid!!.Id!!,
                                            i.ifid!!.userName!!,
                                            i.ifid!!.profilePhoto!!,
                                            i.Id!!,
                                            i.ifid!!.profilePhoto!!,
                                            i.ifid!!.userName!!,
                                            getStringToDateWithDots(i.createdAt!!),
                                            i.price!!,
                                            i.duration!!
                                        )
                                    )
                                }
                                else{
                                    dataList.clear()
                                    dataList.add(
                                        CallHistoryItemModel(
                                            i.uid!!.Id!!,
                                            i.uid!!.userName!!,
                                            i.uid!!.profilePhoto!!,
                                            i.Id!!,
                                            i.uid!!.profilePhoto!!,
                                            i.uid!!.userName!!,
                                            getStringToDateWithDots(i.createdAt!!),
                                            i.price!!,
                                            i.duration!!
                                        )
                                    )
                                }

                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }

                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        com.talktomii.ui.callhistory.CallHistory.recycleview.layoutManager =
                            layoutManager
                        val adapter = CallHistoryAdapter(dataList, webService)
                        com.talktomii.ui.callhistory.CallHistory.recycleview.adapter = adapter
                        com.talktomii.ui.callhistory.CallHistory.progress.visibility = View.GONE
                        com.talktomii.ui.callhistory.CallHistory.recycleview.visibility =
                            View.VISIBLE

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

                override fun onFailure(call: Call<CallHistoryData>, t: Throwable) {
                    cards.value = Resource.error(ApiUtils.failure(t))
                    t.message?.let { Log.d("profile LLL ", it) }
                }

            })
    }

    fun getSearchedCallHistory(text: String) {
        webService.getSearchedCallHistory(
            prefsManager.getString(PrefsManager.PREF_API_ID, ""),
            text
        )
            .enqueue(object : Callback<CallHistoryData> {
                override fun onResponse(
                    call: Call<CallHistoryData>,
                    response: Response<CallHistoryData>
                ) {
                    val dataList = ArrayList<CallHistoryItemModel>()
                    if (response.isSuccessful) {
                        val data = response.body()!!.payload
                        for (i in data!!.callHistory) {
                            try {
                                dataList.add(
                                    CallHistoryItemModel(
                                        i.ifid!!.Id!!,
                                        i.ifid!!.userName!!,
                                        i.ifid!!.profilePhoto!!,
                                        i.Id!!,
                                        i.ifid!!.profilePhoto!!,
                                        i.ifid!!.userName!!,
                                        getStringToDateWithDots(i.createdAt!!),
                                        i.price!!,
                                        i.duration!!
                                    )
                                )
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }

                        }

                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        com.talktomii.ui.callhistory.CallHistory.recycleview.layoutManager =
                            layoutManager
                        val adapter = CallHistoryAdapter(dataList, webService)
                        com.talktomii.ui.callhistory.CallHistory.recycleview.adapter = adapter
                        com.talktomii.ui.callhistory.CallHistory.progress.visibility = View.GONE
                        com.talktomii.ui.callhistory.CallHistory.recycleview.visibility =
                            View.VISIBLE

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

                override fun onFailure(call: Call<CallHistoryData>, t: Throwable) {
                    cards.value = Resource.error(ApiUtils.failure(t))
                    t.message?.let { Log.d("profile LLL ", it) }
                }

            })
    }

    fun deleteCallHistory(id: String) {
        deleteCallHistory.value = Resource.loading()
        webService.deleteCallHistory(id)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Response ------", response.body()!!.message!!)
                        getCallHistory()
                    } else {
                        deleteCallHistory.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    deleteCallHistory.value = Resource.error(ApiUtils.failure(t))
                    Log.d("problem   ::", t.message!!)
                }

            })
    }

    fun blockUser(hashMap: HashMap<String, String>) {
        deleteCallHistory.value = Resource.loading()
        webService.blockUser(hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Response ------", response.body()!!.message!!)
                        getCallHistory()
                    } else {
                        deleteCallHistory.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    deleteCallHistory.value = Resource.error(ApiUtils.failure(t))
                    Log.d("problem   ::", t.message!!)
                }

            })
    }

    fun getNotifications() {
        Log.d(
            "n_data : ",
            prefsManager.getString(
                PrefsManager.PREF_API_ID,
                ""
            ) + "-- " + "Bearer " + prefsManager.getString(PrefsManager.PREF_API_TOKEN, "")
        )
        webService.getNotifications(prefsManager.getString(PrefsManager.PREF_API_ID, ""))
            .enqueue(object : Callback<NotificationData> {
                override fun onResponse(
                    call: Call<NotificationData>,
                    response: Response<NotificationData>
                ) {
                    Timber.d("--%s", response.body().toString())
                    val dataList = ArrayList<NotificationItemModel>()
                    if (response.isSuccessful) {
                        val data = response.body()!!.payload
                        for (i in data!!.notification) {
                            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                            sdf.timeZone = TimeZone.getTimeZone("GMT")
                            try {
                                val time = sdf.parse(i.createdAt).time
                                val now = System.currentTimeMillis()
                                val ago: CharSequence = DateUtils.getRelativeTimeSpanString(
                                    time,
                                    now,
                                    DateUtils.MINUTE_IN_MILLIS
                                )
                                if(i.notificationBy != null){
                                    dataList.add(
                                        NotificationItemModel(
                                            i.Id!!,
                                            i.title!!,
                                            "@" + i.notificationBy!!.userName!!,
                                            i.notificationBy!!.profilePhoto!!,
                                            ago.toString()
                                        )
                                    )
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }


                        }
                        val layoutManager = FlexboxLayoutManager()
                        layoutManager.flexWrap = FlexWrap.WRAP
                        layoutManager.flexDirection = FlexDirection.ROW
                        NotificationFragment.recyclerView.layoutManager = layoutManager
                        val adapter = AdapterTodayNotification(dataList, webService)
                        NotificationFragment.recyclerView.adapter = adapter
                        NotificationFragment.progress.visibility = View.GONE
                        NotificationFragment.recyclerView.visibility = View.VISIBLE
                    } else {
                        notification.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<NotificationData>, t: Throwable) {
                    notification.value = Resource.error(ApiUtils.failure(t))
                    Log.d("notify --- ", t.message!!)
                }

            })
    }

    fun getType() {
        webService.getType()
            .enqueue(object : Callback<ReportAbuseData> {
                override fun onResponse(
                    call: Call<ReportAbuseData>,
                    response: Response<ReportAbuseData>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.payload
                        val types = ArrayList<String>()
                        type_item = HashMap()
                        for (i in data!!.reportAbuse) {
                            val refilldata = i.type
                            if (types.contains(refilldata)) {
                            } else {
                                types.add(refilldata!!)
                                type_item!!.put(i.Id!!, refilldata)
                            }


                        }
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            ReportAbuseActivity.context,
                            android.R.layout.simple_spinner_dropdown_item,
                            types.toMutableList()
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        ReportAbuseActivity.filterTypes!!.setAdapter(adapter)
                        ReportAbuseActivity.filterTypes!!.onItemClickListener =
                            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                                for (entry in type_item!!.entries) {
                                    if (entry.value === adapter.getItem(p2)) {
                                        System.out.println(
                                            "The key for value " + adapter.getItem(p2)
                                                .toString() + " is " + entry.key
                                        )
                                        selectedType = entry.key
                                        break
                                    }
                                }
                            }

                    } else {

                        type.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                        Timber.d("00000" + cards.value!!.message)
                    }
                }

                override fun onFailure(call: Call<ReportAbuseData>, t: Throwable) {
                    type.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun addFeedback(hashMap: HashMap<String, String>) {
        Log.d("Abuse Data :  ", hashMap.toString())
        addFeedback.value = Resource.loading()
        webService.addFeedback(hashMap)
            .enqueue(object : Callback<AddReport> {
                override fun onResponse(
                    call: Call<AddReport>,
                    response: Response<AddReport>
                ) {
                    if (!response.isSuccessful) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            val userMessage = jsonObject.getString("message")
                            val snackbar = Snackbar.make(
                                ReportAbuseActivity.layout,
                                userMessage,
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        val snackbar = Snackbar.make(
                            ReportAbuseActivity.layout,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        ReportAbuseActivity.finishFunction()
                    }
                }

                override fun onFailure(call: Call<AddReport>, t: Throwable) {
                    addFeedback.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun withDrawAmount(hashMap: HashMap<String, String>) {
        webService.getPaid(hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        GetPaidActivity.progressBar.visibility = View.GONE
                        val snackbar = Snackbar.make(
                            GetPaidActivity.layout,
                            response.body()!!.message!!,
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        GetPaidActivity.amount.text.clear()
                        GetPaidActivity.finishFunction()
                        getWallet()
                        getExpenses()
                    } else {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            GetPaidActivity.progressBar.visibility = View.GONE
                            val userMessage = jsonObject.getString("message")
                            val snackbar = Snackbar.make(
                                GetPaidActivity.layout,
                                userMessage,
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                }

            })
    }
}


