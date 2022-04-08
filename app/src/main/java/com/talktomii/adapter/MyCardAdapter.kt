package com.talktomii.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.ui.mycards.data.MyCardsViewModel
import javax.inject.Inject
import android.widget.PopupMenu
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.ui.mycards.data.CardItemsViewModel

class MyCardAdapter(val mList: List<CardItemsViewModel>, var webService: WebService? = null) :
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
        webService = this.webService
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        data = mList[position]
        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_delete -> {
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.mycard_delete_popup)
                        dialog.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        val close = dialog.findViewById(R.id.cancel_btn) as TextView
                        val delete = dialog.findViewById(R.id.deleteCardBtn) as TextView
                        val text = dialog.findViewById(R.id.displayCardNumber) as TextView
                        text.setText(context!!.getString(R.string.delete_card1) + " " + mList[position].last4 + " " + "Card ?")
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        delete.setOnClickListener {
                            Log.d("Delete card is :" ,mList[position].id.trim())
                            if (!::viewModel.isInitialized) {
                                viewModel = MyCardsViewModel(webService!!)
                            }
                            viewModel.deleteCard(mList[position].id.trim())
                            val dialog_delete = Dialog(context!!)
                            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog_delete.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            dialog_delete.setCancelable(false)
                            dialog_delete.setContentView(R.layout.mycard_popup)
                            dialog_delete.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            val close = dialog_delete.findViewById(R.id.closeCardPopup) as TextView
                            close.setOnClickListener {
                                dialog_delete.dismiss()
                                if (!::viewModel.isInitialized) {
                                    viewModel = MyCardsViewModel(webService!!)
                                }
                                viewModel.getCards()
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
        holder.itemName.text = itemsViewModel.last4
        holder.itemImg.setImageResource(itemsViewModel.img)
        holder.moreOptions.setOnClickListener(View.OnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.pop_up_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(moreMenuClickListener())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popupMenu.gravity = Gravity.END
            }
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