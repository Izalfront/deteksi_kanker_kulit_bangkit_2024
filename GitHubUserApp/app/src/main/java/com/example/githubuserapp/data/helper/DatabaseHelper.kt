package com.example.githubuserapp.data.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.AVATAR
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.COMPANY
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FAVORITE
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FOLLOWERS
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.FOLLOWING
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.LOCATION
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.NAME
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.REPOSITORY
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.TABLE_NAME
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "userDB"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
                " ($USERNAME TEXT NOT NULL," +
                " $NAME TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $LOCATION TEXT NOT NULL," +
                " $REPOSITORY TEXT NOT NULL," +
                " $FOLLOWERS TEXT NOT NULL," +
                " $FOLLOWING TEXT NOT NULL," +
                " $FAVORITE TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}