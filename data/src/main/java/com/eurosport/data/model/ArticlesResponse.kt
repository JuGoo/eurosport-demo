package com.eurosport.data.model

data class ArticlesResponse(
    val videos: List<VideoResponse>? = emptyList(),
    val stories: List<StoryResponse>? = emptyList()
)

data class VideoResponse(
    val id: Long?,
    val title: String?,
    val thumb: String?
)

data class StoryResponse(
    val id: Long?,
    val title: String?,
    val image: String?
)