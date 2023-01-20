package com.eurosport.domain.model

sealed class Article {
    abstract val id: Long
    abstract val title: String
    data class Video(override val id: Long, override val title: String): Article()
    data class Story(override val id: Long, override val title: String): Article()
}