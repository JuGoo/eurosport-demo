package com.eurosport.domain.usecase

import com.eurosport.domain.model.Article
import com.eurosport.domain.repositories.ArticlesRepository

class FetchArticlesMixedUseCase(
    private val articlesRepository: ArticlesRepository
) {
    suspend fun run(): Result<List<Article>> = runCatching {
        val result = articlesRepository.fetchArticles().sortedBy { it.date }
        val story: List<Article.Story> = result.filterIsInstance<Article.Story>()
        val videos: List<Article.Video> = result.filterIsInstance<Article.Video>()
        mixToAlternateArticleStory(story, videos)
    }
}

private fun mixToAlternateArticleStory(story: List<Article.Story>, videos: List<Article.Video>) = sequence {
    val first = story.iterator()
    val second = videos.iterator()
    while (first.hasNext() && second.hasNext()) {
        yield(first.next())
        yield(second.next())
    }
    yieldAll(first)
    yieldAll(second)
}.toList()