package com.eurosport.domain.usecase

import com.eurosport.domain.model.Article
import com.eurosport.domain.repositories.ArticlesRepository

class FetchArticlesUseCase(
    private val articlesRepository: ArticlesRepository
) {
    suspend fun run(): Result<List<Article>> = runCatching {
        articlesRepository.fetchArticles()
    }
}