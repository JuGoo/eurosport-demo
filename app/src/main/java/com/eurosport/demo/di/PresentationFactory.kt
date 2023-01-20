package com.eurosport.demo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eurosport.data.factories.RepositoryFactoryImpl
import com.eurosport.demo.feature.detail.DetailViewModel
import com.eurosport.demo.feature.detail.DetailViewModelImpl
import com.eurosport.demo.feature.home.HomeViewModel
import com.eurosport.demo.feature.home.HomeViewModelImpl
import com.eurosport.domain.factories.RepositoryFactory
import com.eurosport.domain.factories.UseCaseFactory

class PresentationFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val result: Any = when (modelClass) {
            HomeViewModel::class.java -> createHomeViewModel()
            DetailViewModel::class.java -> createDetailViewModel()
            else -> throw IllegalStateException("Unsupported view model: $modelClass")
        }
        return checkNotNull(modelClass.cast(result))
    }

    private fun createHomeViewModel() = HomeViewModelImpl(
        fetchArticlesUseCase = createUseCaseFactory().provideFetchArticlesUseCase()
    )

    private fun createDetailViewModel() = DetailViewModelImpl()

    private fun createUseCaseFactory() = UseCaseFactory(createRepositoryFactory())

    private fun createRepositoryFactory(): RepositoryFactory = RepositoryFactoryImpl()
}