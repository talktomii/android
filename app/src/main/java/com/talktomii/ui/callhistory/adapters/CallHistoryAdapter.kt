package com.talktomii.ui.callhistory.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.net.Uri
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.ui.callhistory.activities.CallInvoiceActivity
import com.talktomii.ui.callhistory.models.CallHistoryItemModel
import com.talktomii.ui.loginSignUp.login.LoginFragment
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.utlis.PrefsManager
import javax.inject.Inject
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.talktomii.adapter.MyCardAdapter


class CallHistoryAdapter(
    val mList: List<CallHistoryItemModel>,
    var webService: WebService? = null
) :
    RecyclerView.Adapter<CallHistoryAdapter.ViewHolder>() {

    @Inject
    lateinit var viewModel: MyCardsViewModel

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.call_history_item, parent, false)
        context = parent.context
        webService = this.webService
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_block_user -> {
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.getWindow()!!
                            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.block_user_popup)
                        dialog.getWindow()!!.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        val close = dialog.findViewById(R.id.cancel_btn) as TextView
                        val delete = dialog.findViewById(R.id.deleteCardBtn) as TextView
                        val block_text = dialog.findViewById(R.id.blockCallUserText) as TextView
                        block_text.setText(context!!.getString(R.string.blockedtext) + " " +mList[position].if_Username + " ?")
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        delete.setOnClickListener {
                            if (!::viewModel.isInitialized) {
                                viewModel = MyCardsViewModel(webService!!)
                            }
                            val hashmap = HashMap<String, String>()
                            hashmap["id"] = mList[position].ifid.toString()
                            viewModel.blockUser(hashmap)
                            val dialog_delete = Dialog(context!!)
                            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog_delete.getWindow()!!
                                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            dialog_delete.setCancelable(false)
                            dialog_delete.setContentView(R.layout.confirm_block_user_popup)
                            dialog_delete.getWindow()!!.setLayout(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                            );
                            val close = dialog_delete.findViewById(R.id.closeCardPopup) as TextView
                            val blocked_text = dialog_delete.findViewById(R.id.blocked_user_text) as TextView
                            blocked_text.setText(mList[position].if_Username + " has blocked")
                            close.setOnClickListener {
                                dialog_delete.dismiss()
                            }
                            dialog.hide()
                            dialog_delete.show()
                        }
                        dialog.show()
                    }
                    R.id.action_delete_history -> {
                        if (!::viewModel.isInitialized) {
                            viewModel = MyCardsViewModel(webService!!)
                        }
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.getWindow()!!
                            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.deletecallhistory_popup)
                        dialog.getWindow()!!.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        val close = dialog.findViewById(R.id.cancelcall_btn) as TextView
                        val delete = dialog.findViewById(R.id.deletecallCardBtn) as TextView
                        delete.setOnClickListener {
                            viewModel.deleteCallHistory(mList[position].ifid)
                            val dialog_delete = Dialog(context!!)
                            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog_delete.getWindow()!!
                                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            dialog_delete.setCancelable(false)
                            dialog_delete.setContentView(R.layout.confirmdelete_callhistory)
                            dialog_delete.getWindow()!!.setLayout(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                            );
                            val close =
                                dialog_delete.findViewById(R.id.closeConfirmDeleteHistoryPopup) as TextView
                            val userName =
                                dialog_delete.findViewById(R.id.delete_history_text) as TextView
                            val userPhoto =
                                dialog_delete.findViewById(R.id.callUserIcon) as ImageView
                            userName.text = mList[position].call_name + " has been deleted"
                            Picasso.with(context).load(Uri.parse(mList[position].call_Img))
                                .into(userPhoto);
                            close.setOnClickListener {
                                dialog_delete.dismiss()
                                viewModel.getCallHistory()
                            }
                            dialog.hide()
                            dialog_delete.show()
                        }
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                }
                return false
            }
        }
        class moreMenuClickListenerInfluencer : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_delete_history -> {
                        if (!::viewModel.isInitialized) {
                            viewModel = MyCardsViewModel(webService!!)
                        }
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.getWindow()!!
                            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.deletecallhistory_popup)
                        dialog.getWindow()!!.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        val close = dialog.findViewById(R.id.cancelcall_btn) as TextView
                        val delete = dialog.findViewById(R.id.deletecallCardBtn) as TextView
                        delete.setOnClickListener {
                            viewModel.deleteCallHistory(mList[position].ifid)
                            val dialog_delete = Dialog(context!!)
                            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog_delete.getWindow()!!
                                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            dialog_delete.setCancelable(false)
                            dialog_delete.setContentView(R.layout.confirmdelete_callhistory)
                            dialog_delete.getWindow()!!.setLayout(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                            );
                            val close =
                                dialog_delete.findViewById(R.id.closeConfirmDeleteHistoryPopup) as TextView
                            val userName =
                                dialog_delete.findViewById(R.id.delete_history_text) as TextView
                            val userPhoto =
                                dialog_delete.findViewById(R.id.callUserIcon) as ImageView
                            userName.text = mList[position].call_name + " has been deleted"
                            Picasso.with(context).load(Uri.parse(mList[position].call_Img))
                                .into(userPhoto);
                            close.setOnClickListener {
                                dialog_delete.dismiss()
                                viewModel.getCallHistory()
                            }
                            dialog.hide()
                            dialog_delete.show()
                        }
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                }
                return false
            }
        }
        Picasso.with(context).load(Uri.parse(mList[position].call_Img)).into(holder.itemImg);
//        holder.itemImg.setImageURI(Uri.parse(mList[position].call_Img))
        holder.itemName.text = mList[position].call_name
        holder.itemDate.text = mList[position].call_date
        holder.itemRs.text = "$" + mList[position].call_rs.toString()
        holder.itemTime.text = mList[position].call_time.toString() + " Min"
        val sh: SharedPreferences = context!!.getSharedPreferences("RoleName", MODE_PRIVATE)
        val name = sh.getString("name","")
        holder.moreOptions.setOnClickListener(View.OnClickListener { view ->
            if(name == "user"){
                val wrapper: Context = ContextThemeWrapper(context, R.style.Talk_PopupMenu)
                val popupMenu = PopupMenu(wrapper, view)
                val menuInflater = MenuInflater(context)
                menuInflater.inflate(R.menu.call_history_popup, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(moreMenuClickListener())
                popupMenu.show()
            }else{
                val wrapper: Context = ContextThemeWrapper(context, R.style.Talk_PopupMenu)
                val popupMenu = PopupMenu(wrapper, view)
                val menuInflater = MenuInflater(context)
                menuInflater.inflate(R.menu.call_history_popup_influencer, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(moreMenuClickListenerInfluencer())
                popupMenu.show()
            }

        })
        if(name == "user"){
            holder.detailCallHistory.setOnClickListener {
                val intent = Intent(context, CallInvoiceActivity::class.java)
                intent.putExtra("id",mList[position].id)
                intent.putExtra("if_name",mList[position].if_Username)
                intent.putExtra("image",mList[position].if_profile)
                intent.putExtra("date",mList[position].call_date)
                intent.putExtra("amount",mList[position].call_rs)
                intent.putExtra("duration",mList[position].call_time)
                context!!.startActivity(intent)
            }
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemImg: ImageView = itemView.findViewById(R.id.callHistoryImg)
        val itemName: TextView = itemView.findViewById(R.id.callHistoryName)
        val itemDate: TextView = itemView.findViewById(R.id.callHistoryDate)
        val itemRs: TextView = itemView.findViewById(R.id.callHistoryrs)
        val itemTime: TextView = itemView.findViewById(R.id.callHistoryTime)
        val moreOptions: ImageView = itemView.findViewById(R.id.moreCallOptions)
        val detailCallHistory : LinearLayout = itemView.findViewById(R.id.detailCallHistoryLayout)
    }

}