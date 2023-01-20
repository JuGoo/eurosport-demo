package com.eurosport.data.repositories

import com.eurosport.data.model.ArticlesResponse
import com.eurosport.data.model.StoryResponse
import com.eurosport.data.model.VideoResponse
import com.eurosport.data.services.EurosportService
import com.eurosport.domain.model.Article
import com.eurosport.domain.repositories.ArticlesRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ArticlesRepositoryImpl(
    private val service: EurosportService,
    private val coroutineContext: CoroutineContext
) : ArticlesRepository {
    override suspend fun fetchArticles(): List<Article> = withContext(coroutineContext) {
        service.fetchArticles().mapToArticles()
    }
}

internal fun ArticlesResponse.mapToArticles(): List<Article> =
    (videos?.mapToVideos() ?: emptyList()) + (stories?.mapToStories() ?: emptyList())

internal fun List<VideoResponse>.mapToVideos(): List<Article.Video> = mapNotNull {
    val id = it.id ?: return@mapNotNull null
    val title = it.title ?: return@mapNotNull null
    Article.Video(id = id, title = title)
}

internal fun List<StoryResponse>.mapToStories(): List<Article.Story> = mapNotNull {
    val id = it.id ?: return@mapNotNull null
    val title = it.title ?: return@mapNotNull null
    Article.Story(id = id, title = title)
}