package com.eurosport.demo.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurosport.domain.model.Article
import com.eurosport.domain.usecase.FetchArticlesUseCase
import kotlinx.coroutines.launch

sealed class ArticleItem {
    data class VideoItem(val id: String) : ArticleItem()
    data class StoryItem(val id: String) : ArticleItem()
}

sealed class HomeState {
    object Loading : HomeState()
    data class Data(val items: List<ArticleItem>) : HomeState()
    object Error : HomeState()
}

abstract class HomeViewModel : ViewModel() {
    abstract val state: LiveData<HomeState>

    abstract fun start()
}

class HomeViewModelImpl(
    private val fetchArticlesUseCase: FetchArticlesUseCase
) : HomeViewModel() {
    override val state = MutableLiveData<HomeState>()

    override fun start() = launch {
        state.postValue(HomeState.Loading)
        fetchArticlesUseCase.run().onSuccess { list ->
            state.postValue(HomeState.Data(list.mapToArticleItems()))
        }.onFailure {
            state.postValue(HomeState.Error)
        }
    }
}

private fun List<Article>.mapToArticleItems() = map { article ->
    when (article) {
        is Article.Story -> ArticleItem.StoryItem(article.id.toString())
        is Article.Video -> ArticleItem.VideoItem(article.id.toString())
    }
}

fun ViewModel.launch(block: suspend () -> Unit) {
    viewModelScope.launch { block() }
}

