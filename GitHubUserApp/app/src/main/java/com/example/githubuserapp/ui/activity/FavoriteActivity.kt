package com.example.githubuserapp.ui.activity

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.CONTENT_URI
import com.example.githubuserapp.data.helper.FavoriteHelper
import com.example.githubuserapp.data.helper.MappingHelper
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.ui.adapter.FavouriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeActionBar()
        initializeHelper()

        binding.rvUsersFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvUsersFavorite.setHasFixedSize(true)
        adapter = FavouriteAdapter()
        binding.rvUsersFavorite.adapter = adapter

        val handleThread = HandlerThread("DataObserver")
        handleThread.start()
        val handler = Handler(handleThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadListFavourite()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadListFavourite()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavourite = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavourite)
    }

    private fun initializeActionBar(){
        if (supportActionBar != null) {
            supportActionBar?.title = getString(R.string.title_appbar_favorite)
        }
    }

    private fun initializeHelper(){
        dbHelper = FavoriteHelper.getInstance(applicationContext)
        dbHelper.open()
    }

    private fun loadListFavourite() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.loadingProgressFavorite.visibility = View.VISIBLE
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = deferredFav.await()
            binding.loadingProgressFavorite.visibility = View.INVISIBLE
            if (favData.size > 0) {
                adapter.listFavourite = favData
            } else {
                adapter.listFavourite = ArrayList()
                showSnack()
            }
        }
    }

    private fun showSnack() {
        Snackbar.make(
            binding.rvUsersFavorite,
            getString(R.string.empty_favorite),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        super.onResume()
        loadListFavourite()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        if (menu != null) {
            val search = menu.findItem(R.id.search)
            search.isVisible = false
        }

        val favorite = menu?.findItem(R.id.favorite_page)
        if (favorite != null) {
            favorite.title = "Home Page"
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_page) {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.setting_page) {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"

        private lateinit var adapter: FavouriteAdapter
        private lateinit var dbHelper: FavoriteHelper
    }
}