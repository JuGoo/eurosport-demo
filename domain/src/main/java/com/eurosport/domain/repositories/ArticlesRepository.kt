package com.eurosport.domain.repositories

import com.eurosport.domain.model.Article

interface ArticlesRepository {
    suspend fun fetchArticles(): List<Article>
}