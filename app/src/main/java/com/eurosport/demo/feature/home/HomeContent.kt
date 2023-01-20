package com.eurosport.demo.feature.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.eurosport.demo.ui.component.ErrorScreen
import com.eurosport.demo.ui.component.LoadingScreen

@Composable
fun HomeContent(viewModel: HomeViewModel) {
    when (val state = viewModel.state.observeAsState(HomeState.Loading).value) {
        HomeState.Loading -> LoadingScreen()
        is HomeState.Data -> ArticleListScreen(articles = state.items)
        HomeState.Error -> ErrorScreen()
    }
}


@Composable
fun ArticleListScreen(articles: List<ArticleItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(articles) {
            ArticleItemView(it)
        }
    }
}

@Composable
fun ArticleItemView(item: ArticleItem) = when (item) {
    is ArticleItem.StoryItem -> StoryItemView(item)
    is ArticleItem.VideoItem -> VideoItemView(item)
}

@Composable
fun StoryItemView(item: ArticleItem.StoryItem) = Text("StoryItemView ${item.id}")

@Composable
fun VideoItemView(item: ArticleItem.VideoItem) = Text("VideoItemView ${item.id}")