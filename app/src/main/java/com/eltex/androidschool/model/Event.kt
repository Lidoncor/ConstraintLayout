package com.eltex.androidschool.model

data class Event(
    val id: Long = 0L,
    val content: String = "",
    val author: String = "",
    val published: String = "",
    val likedByMe: Boolean = false,
    val participatedByMe: Boolean = false,
)
