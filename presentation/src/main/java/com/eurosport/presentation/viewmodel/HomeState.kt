package com.eurosport.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurosport.domain.model.Article
import com.eurosport.domain.usecase.FetchArticlesMixedUseCase
import com.eurosport.presentation.model.ArticleItem
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale

sealed class HomeState {
    object Loading : HomeState()
    data class Data(val items: List<ArticleItem>) : HomeState()
    object Error : HomeState()
}

abstract class HomeViewModel : ViewModel() {
    abstract val state: LiveData<HomeState>

    abstract fun start()
}

internal class HomeViewModelImpl(
    private val fetchArticlesUseCase: FetchArticlesMixedUseCase
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
        is Article.Story -> article.mapToStoryItem()
        is Article.Video -> article.mapToVideoItem()
    }
}

private fun Article.Video.mapToVideoItem() = ArticleItem.VideoItem(
    id = id.toString(),
    title = title,
    sport = sport.name.uppercase(),
    imageUrl = imageUrl ?: "",
    videoUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString()),
    views = views
)

private fun Article.Story.mapToStoryItem() = ArticleItem.StoryItem(
    id = id.toString(),
    title = title,
    sport = sport.name.uppercase(),
    imageUrl = imageUrl ?: "",
    author = author ?: "",
    duration = date?.let { createSimpleDateFormat().format(it).toString() } ?: "",
    teaser = teaser ?: ""
)

fun createSimpleDateFormat(): SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

fun ViewModel.launch(block: suspend () -> Unit) {
    viewModelScope.launch { block() }
}
