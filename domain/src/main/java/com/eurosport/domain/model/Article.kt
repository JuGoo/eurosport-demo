package com.eurosport.domain.model

import java.util.Date

sealed class Article {
    abstract val id: Long
    abstract val title: String
    abstract val imageUrl: String?
    abstract val date: Date?
    abstract val sport: Sport

    data class Video(
        override val id: Long,
        override val title: String,
        override val imageUrl: String?,
        override val date: Date?,
        override val sport: Sport,
        val url: String,
        val views: Int
    ) : Article()

    data class Story(
        override val id: Long,
        override val title: String,
        override val imageUrl: String?,
        override val date: Date?,
        override val sport: Sport,
        val teaser: String?,
        val author: String?
    ) : Article()
}

data class Sport(
    val id: Long,
    val name: String
)