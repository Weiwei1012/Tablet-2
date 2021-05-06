package com.cooking.merge.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.cooking.merge.R
import com.cooking.merge.models.HotitemsModel
import com.cooking.merge.search.SearchDetailsActivity
import kotlinx.android.synthetic.main.cardview_layout_search.view.*
import kotlin.collections.ArrayList

class HotitemsAdapter(private var itemList: ArrayList<HotitemsModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var searchList = ArrayList<HotitemsModel>()

    lateinit var mcontext: Context

    init {
        searchList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemListView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_layout_search, parent, false)
        val source = ListHolder(itemListView)
        mcontext = parent.context
        return source
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.IV_search.setImageResource(searchList[position].sf_image)
        holder.itemView.TV_search.text = searchList[position].sf_title
        holder.itemView.TV_search.setTextColor(Color.BLACK)

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, SearchDetailsActivity::class.java)
////            intent.putExtra("passhotIV", searchList[position].sf_image.toString())
////            intent.putExtra("passhotTV", searchList[position].sf_title)
            intent.putExtra("hot",searchList[position].sf_title)
//
            mcontext.startActivity(intent)

            //Toast.makeText(mcontext,"敬請期待",Toast.LENGTH_LONG).show()

            Log.d("Selected:", searchList[position].sf_title)
        }
    }


    inner class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hotIV: ImageView = itemView.IV_search
        var hotTV: TextView = itemView.TV_search

    }

}