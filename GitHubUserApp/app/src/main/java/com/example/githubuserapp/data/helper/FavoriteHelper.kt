package com.example.githubuserapp.data.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.TABLE_NAME
import com.example.githubuserapp.data.database.DataManagement.UserFavoriteColumns.Companion.USERNAME
import java.sql.SQLException

class FavoriteHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavoriteHelper(context)
        }

        private lateinit var database: SQLiteDatabase
    }

    init {
        databaseHelper =
            DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE, null, null, null, null, null, null
        )
    }

    fun queryByUsername(us: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ?", arrayOf(us),
            null, null, null, null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }

    fun deleteById(id: String?): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}