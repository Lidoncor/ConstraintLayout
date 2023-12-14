package com.eltex.androidschool.dao

import com.eltex.androidschool.model.Event

interface EventsDao {
    fun getAll(): List<Event>
    fun save(event: Event): Event
    fun likeById(id: Long): Event
    fun deleteById(id: Long)
    fun participateById(id: Long): Event
    fun editById(id: Long, content: String): Event
}