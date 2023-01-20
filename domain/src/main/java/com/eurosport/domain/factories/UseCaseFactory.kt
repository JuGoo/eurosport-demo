package com.eurosport.domain.factories

import com.eurosport.domain.usecase.FetchArticlesUseCase

class UseCaseFactory(
    private val repositoryFactory: RepositoryFactory
) {

    fun provideFetchArticlesUseCase() = FetchArticlesUseCase(
        articlesRepository = repositoryFactory.provideArticlesRepository()
    )
}
