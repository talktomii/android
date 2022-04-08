package com.talktomii.ui.home.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.databinding.FragmentNotificationsBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.ui.settings.Settings
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NotificationFragment : DaggerFragment() {

    private lateinit var binding: FragmentNotificationsBinding

    @Inject
    lateinit var viewModel: MyCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        recyclerView = binding.rvTodayNotifi
        progress = binding.getNotificationProgress
        progress.visibility = View.VISIBLE
        viewModel.getNotifications()
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
                ft.replace(binding.nFrame.id, Settings(), "NewFragmentTag")
                ft.commit()
            }
        })
    }
    companion object{
        lateinit var recyclerView: RecyclerView
        lateinit var progress: ProgressBar

    }

}