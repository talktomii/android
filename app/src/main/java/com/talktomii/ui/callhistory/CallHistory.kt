package com.talktomii.ui.callhistory

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.CallHistoryBinding
import com.talktomii.ui.callhistory.adapters.CallHistoryAdapter
import com.talktomii.ui.callhistory.models.CallHistoryItemModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CallHistory: DaggerFragment(R.layout.call_history) {

    private lateinit var binding: CallHistoryBinding

    @Inject
    lateinit var viewModel: MyCardsViewModel

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
        progress = binding.getCallHistoryProgress
        progress.visibility = View.VISIBLE
        viewModel.getCallHistory()
        return binding.root
    }

    companion object{
        lateinit var recycleview: RecyclerView
        lateinit var progress : ProgressBar
        lateinit var layout: RelativeLayout
        lateinit var context : Context
        fun getContext(context: Context){
            this.context = context
        }
    }
}