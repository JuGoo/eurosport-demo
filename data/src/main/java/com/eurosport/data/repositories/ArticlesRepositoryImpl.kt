package com.eurosport.data.repositories

import com.eurosport.data.model.ArticlesResponse
import com.eurosport.data.model.SportResponse
import com.eurosport.data.model.StoryResponse
import com.eurosport.data.model.VideoResponse
import com.eurosport.data.services.EurosportService
import com.eurosport.domain.model.Article
import com.eurosport.domain.model.Sport
import com.eurosport.domain.repositories.ArticlesRepository
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.CoroutineContext

internal class ArticlesRepositoryImpl(
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
    val url = it.url ?: return@mapNotNull null
    val sport = it.sport?.mapToSport() ?: return@mapNotNull null
    Article.Video(
        id = id, title = title, imageUrl = it.thumb, date = it.date?.toDate(), sport = sport, url = url, views = it.views ?: 0
    )
}

internal fun List<StoryResponse>.mapToStories(): List<Article.Story> = mapNotNull {
    val id = it.id ?: return@mapNotNull null
    val title = it.title ?: return@mapNotNull null
    val sport = it.sport?.mapToSport() ?: return@mapNotNull null
    Article.Story(
        id = id,
        title = title,
        imageUrl = it.image,
        date = it.date?.toDate(),
        sport = sport,
        teaser = it.teaser,
        author = it.author
    )
}

private fun Double.toDate(): Date = Date((this * 1000).toLong())

private fun SportResponse.mapToSport(): Sport? {
    val id = id ?: return null
    val name = name ?: return null
    return Sport(
        id = id, name = name
    )
}