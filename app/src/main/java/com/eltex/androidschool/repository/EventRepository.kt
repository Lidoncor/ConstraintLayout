package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    fun likeById(id: Long)
    fun participateById(id: Long)
    fun addEvent(content: String)
    fun deleteById(id: Long)
    fun editById(id: Long, content: String)
}