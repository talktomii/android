package com.talktomii.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.apis.WebService
import com.talktomii.ui.banksettings.BankItemModel
import com.talktomii.ui.banksettings.activities.AddBankAccountActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import org.w3c.dom.Text
import javax.inject.Inject

class MyBankAdapter(val mList: List<BankItemModel>, var webService: WebService? = null) :
    RecyclerView.Adapter<MyBankAdapter.ViewHolder>() {

    @Inject
    lateinit var viewModel: MyCardsViewModel

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        var data : BankItemModel?= null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mybank_item, parent, false)
        context = parent.context
        webService = this.webService
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        data = mList[position]
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("RoleName",
            Context.MODE_PRIVATE
        )
        class moreMenuClickListener : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_delete -> {
                        val dialog = Dialog(context!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.mybank_delete_popup)
                        dialog.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        val close = dialog.findViewById(R.id.CancelBankBtn) as TextView
                        val delete = dialog.findViewById(R.id.deleteBankBtn) as TextView
                        val text = dialog.findViewById(R.id.deleteBankDetailText) as TextView
                        text.setText(context!!.getString(R.string.delete_bank) + " " + mList[position].bank_name + " ?")
                        close.setOnClickListener {
                            dialog.dismiss()
                        }
                        delete.setOnClickListener {
                            if (!::viewModel.isInitialized) {
                                viewModel = MyCardsViewModel(webService!!)
                            }
                            viewModel.deleteBank(mList[position].bank_id.trim())
                            val dialog_delete = Dialog(context!!)
                            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog_delete.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            dialog_delete.setCancelable(false)
                            dialog_delete.setContentView(R.layout.mybank_popup)
                            dialog_delete.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            val close = dialog_delete.findViewById(R.id.closeBankPopup) as TextView
                            val t_text = dialog_delete.findViewById(R.id.bankdetailDeletedText) as TextView
                            t_text.setText("Bank details has been deleted")
                            close.setOnClickListener {
                                dialog_delete.dismiss()
                                if (!::viewModel.isInitialized) {
                                    viewModel = MyCardsViewModel(webService!!)
                                }
                                viewModel.getBank(sharedPreferences.getString("id","").toString())
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
        holder.itemName.text = itemsViewModel.bank_name
        holder.moreOptions.setOnClickListener(View.OnClickListener { view ->
            val wrapper: Context = ContextThemeWrapper(context, R.style.Talk_PopupMenu)
            val popupMenu = PopupMenu(wrapper, view)
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.bank_popup, popupMenu.menu)
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
        val itemName: TextView = itemView.findViewById(R.id.tvBankName)
        val moreOptions : RelativeLayout = itemView.findViewById(R.id.moreBankOptions)
    }

}