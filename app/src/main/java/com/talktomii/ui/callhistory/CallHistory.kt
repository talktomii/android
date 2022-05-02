package com.talktomii.ui.callhistory

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.CallHistoryBinding
import com.talktomii.ui.callhistory.models.CallHistoryItemModel
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
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.searchCallHistory.setBackgroundResource(R.drawable.bgscancard_rounder_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.searchCallHistory.setBackgroundResource(R.drawable.bg_scan_card_rounder)
                binding.searchCallHistory.queryHint = Html.fromHtml("<font color = #C5C5C5>" + "Search calls" + "</font>")
                val txtSearch: EditText = binding.searchCallHistory.findViewById(androidx.appcompat.R.id.search_src_text)
                txtSearch.setTextColor(resources.getColor(R.color.gray))
                val searchIcon = binding.searchCallHistory.findViewById(androidx.appcompat.R.id.search_mag_icon) as ImageView
                searchIcon.setImageResource(R.drawable.ic_search2)
                val closeIcon = binding.searchCallHistory.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
                closeIcon.setImageResource(R.drawable.quantum_ic_clear_grey600_24)
            }
        }
        binding.clearHistoryBtn.setOnClickListener {
//            val wrapper: Context = ContextThemeWrapper(context, R.style.Talk_PopupMenu)
//            val popupMenu = PopupMenu(wrapper, view, Gravity.TOP)
//            val menuInflater = MenuInflater(context)
//            menuInflater.inflate(R.menu.clear_history_popup, popupMenu.menu)
//            popupMenu.show()
        }
        binding.searchCallHistory.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchedCallHistory(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchedCallHistory(newText!!)
                return false
            }

        })

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