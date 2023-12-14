package com.eltex.androidschool.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.utils.getIntOrThrow
import com.eltex.androidschool.utils.getLongOrThrow
import com.eltex.androidschool.utils.getStringOrThrow

class EventsDaoImpl(private val db: SQLiteDatabase) : EventsDao {
    override fun getAll(): List<Event> {
        val result = mutableListOf<Event>()

        db.query(
            EventsTable.TABLE_NAME,
            EventsTable.allColumns,
            null,
            null,
            null,
            null,
            "${EventsTable.ID} DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result += cursor.readEvent()
            }
        }

        return result
    }

    override fun save(event: Event): Event {
        val contentValues = contentValuesOf(
            EventsTable.CONTENT to event.content,
            EventsTable.AUTHOR to event.author,
            EventsTable.PUBLISHED to event.published,
            EventsTable.LIKE_BY_ME to event.likedByMe,
            EventsTable.PARTICIPATE_BY_ME to event.participatedByMe,
        )

        if (event.id != 0L) {
            contentValues.put(EventsTable.ID, event.id)
        }

        val id = db.replace(EventsTable.TABLE_NAME, null, contentValues)

        return getEventById(id)
    }

    override fun likeById(id: Long): Event {
        db.execSQL(
            """
            UPDATE ${EventsTable.TABLE_NAME} SET 
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END 
            WHERE id = ?;
        """.trimIndent(),
            arrayOf(id.toString())
        )

        return getEventById(id)
    }

    private fun getEventById(id: Long): Event =
        db.query(
            EventsTable.TABLE_NAME,
            EventsTable.allColumns,
            "${EventsTable.ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToFirst()
            it.readEvent()
        }

    override fun deleteById(id: Long) {
        db.delete(EventsTable.TABLE_NAME, "${EventsTable.ID} = ?", arrayOf(id.toString()))
    }

    override fun participateById(id: Long): Event {
        db.execSQL(
            """
            UPDATE ${EventsTable.TABLE_NAME} SET 
                participatedByMe = CASE WHEN participatedByMe THEN 0 ELSE 1 END 
            WHERE id = ?;
        """.trimIndent(),
            arrayOf(id.toString())
        )

        return getEventById(id)
    }

    override fun editById(id: Long, content: String): Event {
        db.execSQL(
            """
            UPDATE ${EventsTable.TABLE_NAME} SET 
                content = ? 
            WHERE id = ?;
        """.trimIndent(),
            arrayOf(content, id.toString())
        )

        return getEventById(id)
    }

    private fun Cursor.readEvent(): Event =
        Event(
            id = getLongOrThrow(EventsTable.ID),
            content = getStringOrThrow(EventsTable.CONTENT),
            author = getStringOrThrow(EventsTable.AUTHOR),
            published = getStringOrThrow(EventsTable.PUBLISHED),
            likedByMe = getIntOrThrow(EventsTable.LIKE_BY_ME),
            participatedByMe = getIntOrThrow(EventsTable.PARTICIPATE_BY_ME),
        )
}