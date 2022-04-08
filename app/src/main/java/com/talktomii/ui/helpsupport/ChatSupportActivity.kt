package com.talktomii.ui.helpsupport

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.ActivityChatSupportBinding
import com.talktomii.ui.helpsupport.adapters.ViewUserChatAdapter
import com.talktomii.ui.helpsupport.models.UserMessageModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.talktomii.R
import dagger.android.support.DaggerAppCompatActivity


class ChatSupportActivity : DaggerAppCompatActivity(){

    private lateinit var binding: ActivityChatSupportBinding
    lateinit var recycleview: RecyclerView
    val dataList = ArrayList<UserMessageModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_support)
        binding.tvchatBack.setOnClickListener {
            finish()
        }
        recycleview = binding.rvChat
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recycleview.layoutManager = layoutManager
        dataList.add(
            UserMessageModel(
                "Hi Please select a topic you interested in",
                ViewUserChatAdapter.MESSAGE_TYPE_IN
            )
        )
        dataList.add(
            UserMessageModel(
                "Hi Please select a topic you interested in",
                ViewUserChatAdapter.MESSAGE_TYPE_IN
            )
        )
        dataList.add(
            UserMessageModel(
                "ullamco irure culpa ad",
                ViewUserChatAdapter.MESSAGE_TYPE_OUT
            )
        )
        dataList.add(
            UserMessageModel(
                "ullamco irure culpa ad",
                ViewUserChatAdapter.MESSAGE_TYPE_OUT
            )
        )
        dataList.add(
            UserMessageModel(
                "ullamco irure culpa ad",
                ViewUserChatAdapter.MESSAGE_TYPE_OUT
            )
        )
        dataList.add(
            UserMessageModel(
                "Hi Please select a topic you interested in",
                ViewUserChatAdapter.MESSAGE_TYPE_IN
            )
        )
        dataList.add(
            UserMessageModel(
                "ullamco irure culpa ad",
                ViewUserChatAdapter.MESSAGE_TYPE_OUT
            )
        )

        val adapter = ViewUserChatAdapter(this,dataList)
        recycleview.adapter = adapter
    }
}