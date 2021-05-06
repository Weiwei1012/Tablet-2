package com.cooking.merge.bottom_fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooking.merge.R
import com.cooking.merge.adapters.FavitemsAdapter
import com.cooking.merge.adapters.OnFavItemClickListener
import com.cooking.merge.adapters.OnFoodItemClickListener

import com.cooking.merge.favoriteDataBase.FavDataBase
import com.cooking.merge.food_fragment.FoodDetailsf
import com.cooking.merge.models.FavitemsModel
import com.cooking.merge.models.FooditemsModel
import kotlinx.android.synthetic.main.recycler_layout.view.*

class FavoritesFragment: Fragment()  , OnFavItemClickListener {
    private var recyclerView: RecyclerView? = null
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var favList: ArrayList<FavitemsModel>
    private lateinit var favAdapters: FavitemsAdapter
    private lateinit var favdb: FavDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recycler_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //favdb = context?.let { FavDataBase(it) }!!
        favdb = FavDataBase(requireContext())
        recyclerView = view.my_recycler_view

        ///// design the gridlayout & set recyclerview /////
        gridLayoutManager = GridLayoutManager(
            requireContext(), 3,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        ///// design the gridlayout & set recyclerview /////

        favList = ArrayList()
        favList.clear()
        val db = favdb.readableDatabase
        val cursor = favdb.select_all_favorite_list()
        try {
            while (cursor!!.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndex(favdb.ITEM_TITLE))
                val id = cursor.getString(cursor.getColumnIndex(favdb.KEY_ID))
                val image = cursor.getString(cursor.getColumnIndex(favdb.ITEM_IMAGE)).toInt()
                val ingredient = cursor.getString(cursor.getColumnIndex(favdb.ITEM_INGREDIENT))
                val sauce = cursor.getString(cursor.getColumnIndex(favdb.ITEM_SAUCE))
                val link = cursor.getString(cursor.getColumnIndex(favdb.ITEM_LINK))
                val addfav = FavitemsModel(image, title, ingredient, sauce, link, id)
                favList.add(addfav)

            }
        } finally {
            if (cursor != null && cursor.isClosed) cursor.close()
            db.close()
        }
        var fav = favList
        favAdapters =
            FavitemsAdapter(requireContext(), this, favList)   //adapter按照位置擺放foodlist裡的所有物品
        recyclerView?.adapter = favAdapters

    }

    override fun onItemClick(item: FavitemsModel, position: Int) {
        //////receive data from bundle//////

        //////receive data from bundle//////
        val intent = Intent(context, FoodDetailsf::class.java)
        //putExtra("name",favList.title)
        intent.putExtra("FOODIMAGE", item.fav_image.toString())
        intent.putExtra("FOODNAME", item.fav_title)
        intent.putExtra("FOODINGREDIENT", item.fav_ingredient)
        intent.putExtra("FOODSAUCE", item.fav_sauce)
        intent.putExtra("FOODLINK", item.fav_link)
        startActivity(intent)


    }
}






