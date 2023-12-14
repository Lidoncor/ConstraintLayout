package com.eltex.androidschool.dao

object EventsTable {
    const val TABLE_NAME = "Events"
    const val ID = "id"
    const val CONTENT = "content"
    const val AUTHOR = "author"
    const val PUBLISHED = "published"
    const val LIKE_BY_ME = "likedByMe"
    const val PARTICIPATE_BY_ME = "participatedByMe"

    val allColumns = arrayOf(ID, CONTENT, AUTHOR, PUBLISHED, LIKE_BY_ME, PARTICIPATE_BY_ME)
}