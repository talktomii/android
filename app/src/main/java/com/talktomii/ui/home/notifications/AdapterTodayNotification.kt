package com.talktomii.ui.home.notifications

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.databinding.ItemTodayNotificationBinding
import com.talktomii.ui.callhistory.adapters.CallHistoryAdapter
import com.talktomii.ui.mycards.data.CardItemsViewModel
import com.talktomii.ui.mycards.data.MyCardsViewModel
import javax.inject.Inject

class AdapterTodayNotification(
    val mList: List<NotificationItemModel>,
    var webService: WebService? = null
) :
    RecyclerView.Adapter<AdapterTodayNotification.ViewHolder>() {

    @Inject
    lateinit var viewModel: MyCardsViewModel

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        var data: NotificationItemModel? = null
        var update_url: String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_view, parent, false)
        context = parent.context
        webService = this.webService
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        data = mList[position]
        holder.itemName.text = itemsViewModel.n_title
        holder.itemUser.text = itemsViewModel.n_name
        holder.itemDuration.text = itemsViewModel.n_duration
        Glide.with(context!!).load((mList[position].n_image)).error(R.drawable.ic_user)
            .placeholder(R.drawable.ic_user).into(holder.img)
//        Picasso.with(context).load(Uri.parse(mList[position].n_image)).into(holder.img);
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.txtYouAreMissed)
        val itemUser: TextView = itemView.findViewById(R.id.txtUserName)
        val itemDuration: TextView = itemView.findViewById(R.id.txtTime)
        val img: RoundedImageView = itemView.findViewById(R.id.ivCall)
    }


}