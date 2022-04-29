package com.talktomii.ui.mycards.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.databinding.FragmentCardBinding
import com.talktomii.di.SingleLiveEvent
import com.talktomii.ui.mycards.MyCardsVM
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.ui.mycards.data.getCardDetail
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import android.widget.ProgressBar
import android.widget.RelativeLayout


class CardFragment : DaggerFragment(R.layout.fragment_card) {

    private lateinit var binding: FragmentCardBinding
    private val viewModels by viewModels<MyCardsVM>()

    @Inject
    lateinit var viewModel: MyCardsVM
    val cards by lazy { SingleLiveEvent<Resource<getCardDetail>>() }
    @Inject
    lateinit var dataModel: MyCardsViewModel
    lateinit var webService : WebService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)
//        binding.vm = viewModels
        getContext(requireContext())
        recycleview = binding.rvCards
        progress = binding.displayCardProgress
        layout = binding.cardlistLayout
        progress.visibility  = View.VISIBLE
        recycleview.visibility = View.GONE
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("RoleName",
            Context.MODE_PRIVATE
        )
        dataModel.getCards(sharedPreferences.getString("id","").toString())
        return binding.root
    }

    companion object{
        lateinit var recycleview: RecyclerView
        lateinit var progress : ProgressBar
        lateinit var layout : RelativeLayout
        lateinit var context: Context
        fun getContext(context: Context){
            this.context = context
        }
    }
}