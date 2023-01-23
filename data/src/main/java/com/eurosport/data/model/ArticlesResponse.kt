package com.eurosport.data.model

internal data class ArticlesResponse(
    val videos: List<VideoResponse>? = emptyList(),
    val stories: List<StoryResponse>? = emptyList()
)

internal data class VideoResponse(
    val id: Long?,
    val title: String?,
    val thumb: String?,
    val url: String?,
    val date: Double?,
    val sport: SportResponse?,
    val views: Int?
)

internal data class StoryResponse(
    val id: Long?,
    val title: String?,
    val teaser: String?,
    val image: String?,
    val date: Double?,
    val author: String?,
    val sport: SportResponse?
)

internal data class SportResponse(
    val id: Long?,
    val name: String?,
)