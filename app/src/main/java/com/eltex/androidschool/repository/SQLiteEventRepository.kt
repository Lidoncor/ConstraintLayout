package com.eltex.androidschool.repository

import com.eltex.androidschool.dao.EventsDao
import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SQLiteEventRepository(private val dao: EventsDao) : EventRepository {
    private val state = MutableStateFlow(readEvents())
    private fun readEvents(): List<Event> = dao.getAll()
    override fun getEvents(): Flow<List<Event>> = state

    override fun likeById(id: Long) {
        dao.likeById(id)

        state.update { readEvents() }
    }

    override fun participateById(id: Long) {
        dao.participateById(id)

        state.update { readEvents() }
    }

    override fun addEvent(content: String) {
        dao.save(Event(content = content, author = "Student"))

        state.update { readEvents() }
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)

        state.update { readEvents() }
    }

    override fun editById(id: Long, content: String) {
        dao.editById(id, content)

        state.update { readEvents() }
    }
}