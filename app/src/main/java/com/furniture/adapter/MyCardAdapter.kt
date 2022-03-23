package com.furniture.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furniture.ui.mycards.data.MyCardsViewModel
import javax.inject.Inject
import android.view.MenuItem
import android.widget.PopupMenu
import android.view.MenuInflater
import com.furniture.R
import com.furniture.data.apis.WebService
import com.furniture.ui.mycards.activities.MyCardsActivity
import com.furniture.ui.mycards.data.CardItemsViewModel


class MyCardAdapter(val mList: List<CardItemsViewModel>) :
    RecyclerView.Adapter<MyCardAdapter.ViewHolder>() {

    @Inject
    lateinit var viewModel: MyCardsViewModel

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        var data : CardItemsViewModel ?= null
        var update_url : String ?= null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mycards_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        data = mList[position]
        Log.d("data is :::", data!!.id)
        update_url = mList[position].id
        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_edit -> {
                        val intent = Intent(context,MyCardsActivity::class.java)
                        intent.putExtra("update","update")
                        intent.putExtra("id",mList[position].id)
                        intent.putExtra("uid",mList[position].uid)
                        intent.putExtra("cardnumber",mList[position].card_Number)
                        intent.putExtra("cardholder",mList[position].card_holder)
                        intent.putExtra("cvv",mList[position].cvv)
                        intent.putExtra("expiredate",mList[position].expire_date)
                        context!!.startActivity(intent)
                    }
                    R.id.action_delete -> {
                        viewModel.deleteCard()
                    }
                }
                return false
            }
        }
        holder.itemName.text = itemsViewModel.card_Number
        holder.itemImg.setImageResource(itemsViewModel.card_Img)
        holder.moreOptions.setOnClickListener(View.OnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.pop_up_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(moreMenuClickListener())
            popupMenu.show()
        })

    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvcardNumber)
        val itemImg : ImageView = itemView.findViewById(R.id.tvCardImage)
        val moreOptions : ImageView = itemView.findViewById(R.id.moreCardOptions)
    }

}