package com.eurosport.data.model

data class ArticlesResponse(
    val videos: List<VideoResponse>? = emptyList(),
    val stories: List<StoryResponse>? = emptyList()
)

data class VideoResponse(
    val id: Long?,
    val title: String?,
    val thumb: String?,
    val url: String?,
    val date: Double?,
    val sport: SportResponse?,
    val views: Int?
)

data class StoryResponse(
    val id: Long?,
    val title: String?,
    val teaser: String?,
    val image: String?,
    val date: Double?,
    val author: String?,
    val sport: SportResponse?
)

data class SportResponse(
    val id: Long?,
    val name: String?,
)