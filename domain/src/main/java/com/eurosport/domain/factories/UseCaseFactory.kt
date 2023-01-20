package com.eurosport.domain.factories

import com.eurosport.domain.usecase.FetchArticlesMixedUseCase

class UseCaseFactory(
    private val repositoryFactory: RepositoryFactory
) {

    fun provideFetchArticlesUseCase() = FetchArticlesMixedUseCase(
        articlesRepository = repositoryFactory.provideArticlesRepository()
    )
}
