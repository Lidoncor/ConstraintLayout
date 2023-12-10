package com.eltex.androidschool.repository

import android.content.Context
import androidx.core.content.edit
import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocalEventsRepository (
    private val context: Context
) : EventRepository {

    companion object{
        private const val EVENTS_FILE_NAME = "events.json"
        private const val NEXT_ID_KEY = "NEXT_ID_KEY"
    }

    private val preferences = context.getSharedPreferences("eventsIds", Context.MODE_PRIVATE)
    private val state = MutableStateFlow(readEvents())
    private var nextId = preferences.getLong(NEXT_ID_KEY, 0L)

    private fun readEvents(): List<Event> {
        val file = context.filesDir.resolve(EVENTS_FILE_NAME)
        val eventsSerialized = if (file.exists()) {
            file.bufferedReader()
                .use {
                    it.readLine()
                }
        } else {
            null
        }
        return if (eventsSerialized != null) {
            Json.decodeFromString(eventsSerialized)
        } else {
            emptyList()
        }
    }

    override fun getEvents(): Flow<List<Event>> = state.asStateFlow()

    override fun likeById(id: Long) {
        state.update {
            it.map { event ->
                if (event.id == id) {
                    event.copy(likedByMe = !event.likedByMe)
                } else {
                    event
                }
            }
        }
        sync()
    }

    override fun participateById(id: Long) {
        state.update {
            it.map { event ->
                if (event.id == id) {
                    event.copy(participatedByMe = !event.participatedByMe)
                } else {
                    event
                }
            }
        }
        sync()
    }

    override fun addEvent(content: String) {
        state.update { events ->
            buildList(capacity = events.size + 1) {
                add(Event(id = ++nextId, content = content, author = "Student"))
                addAll(events)
            }
        }
        sync()
    }

    override fun deleteById(id: Long) {
        state.update { events ->
            events.filter { it.id != id }
        }
        sync()
    }

    override fun editById(id: Long, content: String) {
        state.update {
            it.map { event ->
                if (event.id == id) {
                    event.copy(content = content)
                } else {
                    event
                }
            }
        }
        sync()
    }

    private fun sync(){
        val eventsSerialized = Json.encodeToString(state.value)
        context.filesDir.resolve(EVENTS_FILE_NAME)
            .bufferedWriter()
            .use {
                it.write(eventsSerialized)
            }
        preferences.edit {
            putLong(NEXT_ID_KEY, nextId)
        }
    }
}