package com.example.githubuserapp.ui.activity

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.SettingPreferences
import com.example.githubuserapp.ViewModelFactory
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.adapter.ListUserAdapter
import com.example.githubuserapp.ui.viewmodel.MainViewModel
import com.example.githubuserapp.ui.viewmodel.UserViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeDarkMode()
        initializeData()
        configurationView()
        dataGitReceived()
        userViewModelConfig(listUserAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.find_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listDataUser.clear()
                    configurationView()
                    showLoading(true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        listDataUser.clear()
                        configurationView()
                        userViewModel.getDataGitSearch(query, applicationContext)
                        userViewModelConfig(listUserAdapter)
                    }, 1000L)

                } else {
                    return true
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_page) {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.setting_page) {
            val mIntent = Intent(this, SettingsActivity::class.java)
            startActivity(mIntent)
        }

        return super.onOptionsItemSelected(item)
    }

//    Declaration Function
    private fun initializeDarkMode(){
    val pref = SettingPreferences.getInstance(dataStore)
    val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
        MainViewModel::class.java
    )

    mainViewModel.getThemeSettings().observe(this,
        { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    private fun initializeData(){
        listUserAdapter = ListUserAdapter(listDataUser)
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
    }

    private fun dataGitReceived() {
        userViewModel.getDataGit(applicationContext)
        showLoading(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun configurationView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)

        listUserAdapter.notifyDataSetChanged()
        binding.rvUsers.adapter = listUserAdapter

    }

    private fun userViewModelConfig(adapter: ListUserAdapter) {
        userViewModel.getListUsers().observe(this, { listUsers ->
            if (listUsers != null) {
                listUsers.distinct()


                listDataUserAdapter = (listUsers.distinctBy { it.name } as ArrayList<User>?)!!
                adapter.setData(listUsers.distinctBy { it.name } as ArrayList<User>)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.loadingProgress.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName

        private var listDataUser : ArrayList<User> = ArrayList()
        private lateinit var listUserAdapter: ListUserAdapter
        private var listDataUserAdapter : ArrayList<User> = ArrayList()
        private lateinit var userViewModel: UserViewModel
    }
}