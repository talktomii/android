package com.talktomii.ui.editpersonalinfo.location

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.model.Place
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R

class PlacesAutoCompleteRecyclerAdapter(mContext: Context, val placesApi: PlaceAPI, etSearch: EditText, onClickInteface: OnItemClickInterface) : RecyclerView.Adapter<PlacesAutoCompleteRecyclerAdapter.ViewHolder>(), Filterable {

    //  ArrayAdapter<Place>(mContext, layout.autocomplete_list_item)
    var context: Context
    var etSearch: EditText
    var onClickInteface: OnItemClickInterface
    var resultList: ArrayList<Place>? = ArrayList()

    init {
        this.context = mContext
        this.etSearch = etSearch
        this.onClickInteface = onClickInteface
        this.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().equals("")) {
                    setSearchdata(s.toString())
                } else {
                    resultList!!.clear()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    private fun bindView(viewHolder: ViewHolder, place: Place, position: Int) {
        if (!resultList.isNullOrEmpty()) {
            if (position != resultList!!.size - 1) {
                viewHolder.description?.text = place.description
                viewHolder.footerImageView?.visibility = View.GONE
                viewHolder.description?.visibility = View.VISIBLE
            } else {
                viewHolder.footerImageView?.visibility = View.VISIBLE
                viewHolder.description?.visibility = View.GONE
                viewHolder.ivLocation?.visibility = View.GONE
                viewHolder.view?.visibility = View.GONE
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetChanged()
                }
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    resultList = placesApi.autocomplete(constraint.toString())
                    resultList?.add(Place("-1", "footer"))
                    filterResults.values = resultList
                    filterResults.count = resultList!!.size
                }
                return filterResults
            }
        }
    }

    private fun setSearchdata(searchdata: String) {
        filter.filter(searchdata)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView? = itemView.findViewById(R.id.tvName)
        var footerImageView: ImageView? = itemView.findViewById(R.id.footerImageView)
        var ivLocation: ImageView? = itemView.findViewById(R.id.ivLocation)
        var view: View? = itemView.findViewById(R.id.view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.autocomplete_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var view = holder.itemView
//        val viewHolder: ViewHolder
//        if (view == null) {
//            viewHolder = ViewHolder(holder.itemView)
//            val inflater = LayoutInflater.from(context)
//            view = inflater.inflate(R.layout.autocomplete_list_item, parent, false)
//            viewHolder.description = view.findViewById(R.id.autocompleteText) as TextView
//            viewHolder.footerImageView = view.findViewById(R.id.footerImageView) as ImageView
//            view.tag = viewHolder
//        } else {
//            viewHolder = view.tag as ViewHolder
//        }
        val place = resultList!![position]
        bindView(holder, place, position)
        holder.itemView.setOnClickListener {
            onClickInteface.onClick(place)
        }
    }

    override fun getItemCount(): Int {
        return when {
            resultList.isNullOrEmpty() -> 0
            else -> resultList?.size!!
        }
    }

    interface OnItemClickInterface {
        fun onClick(place: Place)
    }
}

