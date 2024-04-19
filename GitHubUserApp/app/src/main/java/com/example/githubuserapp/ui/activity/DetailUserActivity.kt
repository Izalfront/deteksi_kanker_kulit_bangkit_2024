package com.example.githubuserapp.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.AVATAR
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.COMPANY
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.CONTENT_URI
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FAVORITE
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FOLLOWERS
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FOLLOWING
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.LOCATION
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.NAME
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.REPOSITORY
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.USERNAME
import com.example.githubuserapp.data.helper.FavoriteHelper
import com.example.githubuserapp.databinding.ActivityUserDetailBinding
import com.example.githubuserapp.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserDetailBinding
    private var isFavorite = false
    private lateinit var favHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        favHelper = FavoriteHelper.getInstance(applicationContext)
        favHelper.open()

        initializeActionBar()
        initializeFavorite()
        callingData()

        binding.btnFavorite.setOnClickListener(this)

        configurationSectionPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        if (menu != null) {
            val item = menu.findItem(R.id.search)
            item.isVisible = false
        }

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun initializeActionBar(){
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = getString(R.string.detail_user)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeFavorite(){
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val cursor: Cursor = favHelper.queryByUsername(user.username.toString())
        if (cursor.moveToNext()) {
            isFavorite = true
            setStatusFavorite(true)
        }
    }

    private fun configurationSectionPager() {
        val viewPagerDetail = SectionPagerAdapter(this)
        binding.viewPager.adapter = viewPagerDetail
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }


    private fun callingData() {
        showLoading(true)

        Handler(Looper.getMainLooper()).postDelayed({
            showLoading(false)
            val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
            val image = user.avatar
            nameUser = user.name.toString()
            userName = user.username.toString()
            repository = user.repository.toString()+" Repositori"
            followers = user.followers.toString()+" Pengikut"
            following = user.following.toString()+" Diikuti"
            company = "Bekerja di " + user.company.toString()
            location = "Berlokasi di " + user.location.toString()

            Glide.with(this).load(image).into(binding.ivAvatarReceived)
            binding.tvItemUsername.text = userName
            binding.tvNameReceived.text = nameUser
            binding.tvItemsFollowing.text = following
            binding.tvItemsRepository.text = repository
            binding.tvItemsFollowers.text = followers
            binding.tvItemsCompany.text = company
            binding.tvItemsLocation.text = location
        }, 1000L)

    }

    private fun showLoading(state: Boolean) {
        binding.loadingProgressUserDetail.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onClick(view: View) {
        val data = intent.getParcelableExtra<User>(EXTRA_USER)!!
        if (view.id == R.id.btn_favorite) {
            if (isFavorite) {
                favHelper.deleteById(data.username.toString())
                Toast.makeText(this, getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show()
                setStatusFavorite(false)
                isFavorite = false
            } else {
                val values = ContentValues()
                values.put(USERNAME, data.username)
                values.put(NAME, data.name)
                values.put(AVATAR, data.avatar)
                values.put(COMPANY, data.company)
                values.put(LOCATION, data.location)
                values.put(REPOSITORY, data.repository)
                values.put(FOLLOWERS, data.followers)
                values.put(FOLLOWING, data.following)
                values.put(FAVORITE, "isFav")

                isFavorite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                setStatusFavorite(true)
            }
        }
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status) {
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_50)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_50)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favHelper.close()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private lateinit var nameUser: String
        private lateinit var userName: String
        private lateinit var repository: String
        private lateinit var following: String
        private lateinit var followers: String
        private lateinit var company: String
        private lateinit var location: String
    }

}