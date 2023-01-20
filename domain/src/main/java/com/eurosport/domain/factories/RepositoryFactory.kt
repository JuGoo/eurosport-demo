package com.eurosport.domain.factories

import com.eurosport.domain.repositories.ArticlesRepository

interface RepositoryFactory {

    fun provideArticlesRepository(): ArticlesRepository
}
