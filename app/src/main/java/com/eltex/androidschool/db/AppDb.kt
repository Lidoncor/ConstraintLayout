package com.eltex.androidschool.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.eltex.androidschool.dao.EventsDaoImpl

class AppDb private constructor(db: SQLiteDatabase) {
    val eventDao = EventsDaoImpl(db)

    // companion object всегда 1 в приложении
    companion object {

        // Глобальная переменная в 1 экземпляре
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb {
            // Если до этого создавали INSTANCE, то возвращаем
            INSTANCE?.let { return it }

            // На всякий случай возьмём applicationContext, если случайно передали Activity или Service
            val application = context.applicationContext

            synchronized(this) {
                INSTANCE?.let { return it }

                val appDb = AppDb(DbHelper(application).writableDatabase)

                INSTANCE = appDb

                return appDb
            }
        }
    }
}