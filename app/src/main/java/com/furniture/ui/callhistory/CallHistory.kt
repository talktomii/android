package com.furniture.ui.callhistory

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.databinding.CallHistoryBinding
import com.furniture.ui.callhistory.adapters.CallHistoryAdapter
import com.furniture.ui.callhistory.models.CallHistoryItemModel
import com.furniture.ui.mywallet.adapters.WalletEarningAdapter
import com.furniture.ui.mywallet.fragments.EarningFragment
import com.furniture.ui.mywallet.models.WalletEarningItemModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment

class CallHistory: DaggerFragment(R.layout.call_history) {

    private lateinit var binding: CallHistoryBinding

    val dataList = ArrayList<CallHistoryItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CallHistoryBinding.inflate(inflater, container, false)
        binding.clearHistoryBtn.setOnClickListener {
            val popupMenu = PopupMenu(context, view, Gravity.TOP)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.clear_history_popup, popupMenu.menu)
            popupMenu.show()
        }
        recycleview = binding.rvCallHistory
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recycleview.layoutManager = layoutManager
        dataList.add(
            CallHistoryItemModel(
                R.drawable.ic_user,
                "Leslie Alexander",
                "13.02.2022  2:00 PM",
                "$200,00",
                "45min"
            )
        )

        dataList.add(
            CallHistoryItemModel(
                R.drawable.ic_user,
                "Leslie abc",
                "13.02.2022  2:00 PM",
                "$200,00",
                "45min"
            )
        )

        dataList.add(
            CallHistoryItemModel(
                R.drawable.ic_user,
                "Leslie xyz",
                "13.02.2022  2:00 PM",
                "$200,00",
                "45min"
            )
        )
        val adapter = CallHistoryAdapter(dataList)
        recycleview.adapter = adapter

        return binding.root
    }

    companion object{
        lateinit var recycleview: RecyclerView
    }
}