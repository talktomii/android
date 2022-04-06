package com.furniture.ui.tellusmore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.furniture.R
import com.furniture.adapter.TopicsAdapter
import com.furniture.databinding.TellUsMoreBinding
import com.furniture.utlis.PrefsManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TellUsMore : DaggerFragment(R.layout.tell_us_more) {

    private val args by navArgs<TellUsMoreArgs>()
    private lateinit var binding: TellUsMoreBinding
    private val viewModels by viewModels<TellUsMoreVM>()

    @Inject
    lateinit var prefsManager: PrefsManager


    @Inject
    lateinit var viewModel: TellUsMoreVM

    private val topicsAdapter: TopicsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TellUsMoreBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.rvTopics
        viewModels.isUser.set(args.isUser)
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager


    }


    private fun addItemsOnRecycler() {
        val recyclerview = binding.rvTopics


        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager


        val dataList = ArrayList<ItemsViewModel>()

        dataList.add(ItemsViewModel("Religion"))
        dataList.add(ItemsViewModel("Technology"))
        dataList.add(ItemsViewModel("Philosophy"))
        dataList.add(ItemsViewModel("Cryptocurrency"))
        dataList.add(ItemsViewModel("Music"))
        dataList.add(ItemsViewModel("Movie"))
        dataList.add(ItemsViewModel("Entrepreneurship"))
        dataList.add(ItemsViewModel("Psychology"))
        dataList.add(ItemsViewModel("Sociology"))

        dataList.add(ItemsViewModel("Religion"))
        dataList.add(ItemsViewModel("Technology"))
        dataList.add(ItemsViewModel("Philosophy"))
        dataList.add(ItemsViewModel("Cryptocurrency"))
        dataList.add(ItemsViewModel("Music"))
        dataList.add(ItemsViewModel("Movie"))
        dataList.add(ItemsViewModel("Entrepreneurship"))
        dataList.add(ItemsViewModel("Psychology"))
        dataList.add(ItemsViewModel("Sociology"))

        val adapter = TopicsAdapter(dataList)

        recyclerview.adapter = adapter
    }
}