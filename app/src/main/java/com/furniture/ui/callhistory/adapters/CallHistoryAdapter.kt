package com.furniture.ui.callhistory.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furniture.R
import com.furniture.adapter.MyCardAdapter
import com.furniture.ui.callhistory.models.CallHistoryItemModel
import com.furniture.ui.mycards.activities.MyCardsActivity

class CallHistoryAdapter(val mList: List<CallHistoryItemModel>) :
    RecyclerView.Adapter<CallHistoryAdapter.ViewHolder>() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.call_history_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_block_user -> {
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.block_user_popup)
                        dialog.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        val close = dialog.findViewById(R.id.cancel_btn) as TextView
                        val delete = dialog.findViewById(R.id.deleteCardBtn) as TextView
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        delete.setOnClickListener {
                            val dialog_delete = Dialog(context!!)
                            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog_delete.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            dialog_delete.setCancelable(false)
                            dialog_delete.setContentView(R.layout.confirm_block_user_popup)
                            dialog_delete.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            val close = dialog_delete.findViewById(R.id.closeCardPopup) as TextView
                            close.setOnClickListener {
                                dialog_delete.dismiss()
                            }
                            dialog.hide()
                            dialog_delete.show()
                        }
                        dialog.show()
                    }
                }
                return false
            }
        }
        val itemsViewModel = mList[position]
        holder.itemImg.setImageResource(mList[position].call_Img)
        holder.itemName.text = mList[position].call_name
        holder.itemDate.text = mList[position].call_date
        holder.itemRs.text = mList[position].call_rs
        holder.itemTime.text = mList[position].call_time
        holder.moreOptions.setOnClickListener(View.OnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.call_history_popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(moreMenuClickListener())
            popupMenu.show()
        })

    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemImg : ImageView = itemView.findViewById(R.id.callHistoryImg)
        val itemName: TextView = itemView.findViewById(R.id.callHistoryName)
        val itemDate: TextView = itemView.findViewById(R.id.callHistoryDate)
        val itemRs : TextView = itemView.findViewById(R.id.callHistoryrs)
        val itemTime : TextView = itemView.findViewById(R.id.callHistoryTime)
        val moreOptions : ImageView = itemView.findViewById(R.id.moreCallOptions)
    }

}