package com.talktomii.ui.FAQ

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R


class FAQAdapter(mList: List<FaqModel>) :
    RecyclerView.Adapter<FAQAdapter.ViewHolder>() {
    private var context: Context? = null
    private val inflater: LayoutInflater? = null
    private val data: List<FaqModel>

    init {
        this.data = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView: View = LayoutInflater.from(parent.context).inflate(R.layout.faq_data, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val itemsViewModel = data[position]
        holder.itemQue.text = itemsViewModel.faq_que
        holder.itemAns.text = itemsViewModel.faq_ans
        holder.itemImg.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                holder.baseCard,
                AutoTransition()
            )
            holder.hiddenView.visibility = View.VISIBLE
            holder.itemImg.visibility = View.GONE
            holder.itemImg1.visibility = View.VISIBLE
        }
        holder.itemImg1.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                holder.baseCard,
                AutoTransition()
            )
            holder.hiddenView.visibility = View.GONE
            holder.itemImg.visibility = View.VISIBLE
            holder.itemImg1.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemQue : TextView = itemView.findViewById(R.id.heading)
        val itemAns : TextView = itemView.findViewById(R.id.heading_ans)
        val itemImg : ImageView = itemView.findViewById(R.id.arrow_button)
        val itemImg1 : ImageView = itemView.findViewById(R.id.arrow_button1)
        val fixedLayout : RelativeLayout = itemView.findViewById(R.id.fixed_layout)
        val hiddenView : LinearLayout = itemView.findViewById(R.id.hidden_view)
        val baseCard : CardView = itemView.findViewById(R.id.base_cardview)
    }


}