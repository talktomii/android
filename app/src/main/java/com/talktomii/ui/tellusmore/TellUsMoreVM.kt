package com.talktomii.ui.tellusmore

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.talktomii.R
import com.talktomii.data.model.Interest
import com.talktomii.recycleradapter.AbstractModel
import com.talktomii.recycleradapter.RecyclerAdapter
import javax.inject.Inject

class TellUsMoreVM @Inject constructor() : ViewModel() {

    var isUser: ObservableBoolean = ObservableBoolean()

    val topicsAdapter by lazy { RecyclerAdapter<ItemsViewModel>(R.layout.preferred_topics_item) }

    private val topicsAdapterClick = RecyclerAdapter.OnItemClick { view, viewHolder, type ->
        when (view.id) {
            R.id.clItems -> {
                topicsAdapter.getItemAt(
                    viewHolder?.adapterPosition ?: 0
                ).isClicked.set(
                    !topicsAdapter.getItemAt(
                        viewHolder?.adapterPosition ?: 0
                    ).isClicked.get()
                )

            }
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivInterest -> view.findNavController()
                .navigate(R.id.action_profile_to_editInterestFragment)

            R.id.btnNext -> view.findNavController()
                .navigate(R.id.action_tellUsMore_to_homeFragment)
        }
    }




    init {
        topicsAdapter.setOnItemClick(topicsAdapterClick)
    }

}


data class ItemsViewModel(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val image: String,
    val name: String,
    var isClicked: ObservableBoolean = ObservableBoolean(false),
    val updatedAt: String
) : AbstractModel()