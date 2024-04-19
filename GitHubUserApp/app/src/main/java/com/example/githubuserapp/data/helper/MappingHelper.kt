package com.example.githubuserapp.data.helper

import android.database.Cursor
import com.example.githubuserapp.data.User
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.AVATAR
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.COMPANY
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FAVORITE
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FOLLOWERS
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FOLLOWING
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.LOCATION
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.NAME
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.REPOSITORY
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.USERNAME

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val favList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val username =
                    getString(getColumnIndexOrThrow(USERNAME))
                val name =
                    getString(getColumnIndexOrThrow(NAME))
                val avatar =
                    getString(getColumnIndexOrThrow(AVATAR))
                val company =
                    getString(getColumnIndexOrThrow(COMPANY))
                val location =
                    getString(getColumnIndexOrThrow(LOCATION))
                val repository =
                    getString(getColumnIndexOrThrow(REPOSITORY))
                val followers =
                    getString(getColumnIndexOrThrow(FOLLOWERS))
                val following =
                    getString(getColumnIndexOrThrow(FOLLOWING))
                val isFav =
                    getString(getColumnIndexOrThrow(FAVORITE))

                favList.add(
                    User(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        isFav
                    )
                )
            }
        }
        return favList
    }

}