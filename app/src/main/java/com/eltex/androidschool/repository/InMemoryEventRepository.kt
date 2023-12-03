package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryEventRepository : EventRepository {
    private val state = MutableStateFlow(
        Event(
            id = 1L,
            content = "Слушайте, а как вы относитесь к тому, чтобы собраться большой компанией и поиграть в настолки? У меня есть несколько клевых игр, можем устроить вечер настолок! Пишите в лс или звоните",
            author = "Lydia Westervelt",
            published = "11.05.22 11:21",
        )
    )

    override fun getEvent(): Flow<Event> = state.asStateFlow()

    override fun like() {
        state.update { it.copy(likedByMe = !it.likedByMe) }
    }

    override fun participate() {
        state.update { it.copy(participatedByMe = !it.participatedByMe) }
    }

}