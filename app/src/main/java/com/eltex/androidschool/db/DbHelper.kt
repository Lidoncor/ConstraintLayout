package com.eltex.androidschool.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.eltex.androidschool.dao.EventsTable

class DbHelper(context: Context) : SQLiteOpenHelper(context, "app_db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE ${EventsTable.TABLE_NAME} (
                ${EventsTable.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${EventsTable.CONTENT} TEXT NOT NULL,
                ${EventsTable.AUTHOR} TEXT NOT NULL,
                ${EventsTable.PUBLISHED} TEXT NOT NULL,
                ${EventsTable.LIKE_BY_ME} INTEGER NOT NULL DEFAULT 0,
                ${EventsTable.PARTICIPATE_BY_ME} INTEGER NOT NULL DEFAULT 0
            );
        """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}