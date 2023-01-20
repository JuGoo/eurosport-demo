package com.eurosport.data.factories

import com.eurosport.data.api.ApiClient
import com.eurosport.data.api.ApiClientImpl
import com.eurosport.data.repositories.ArticlesRepositoryImpl
import com.eurosport.data.services.EurosportService
import com.eurosport.domain.factories.RepositoryFactory
import com.eurosport.domain.repositories.ArticlesRepository
import kotlinx.coroutines.Dispatchers

class RepositoryFactoryImpl : RepositoryFactory {
    override fun provideArticlesRepository(): ArticlesRepository = ArticlesRepositoryImpl(
        service = provideEurosportService(),
        coroutineContext = Dispatchers.IO
    )

    private fun provideEurosportService(): EurosportService = provideApiClient().getEurosportService()

    private fun provideApiClient(): ApiClient = ApiClientImpl()
}