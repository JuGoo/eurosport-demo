package com.eurosport.demo.feature.home

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.eurosport.demo.MainActivity
import com.eurosport.demo.R
import com.eurosport.demo.ui.component.ErrorScreen
import com.eurosport.demo.ui.component.LoadingScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDetail: (ArticleItem.StoryItem) -> Unit,
    navigateToPlayer: (String) -> Unit,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    (context as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    when (val state = viewModel.state.observeAsState(HomeState.Loading).value) {
        HomeState.Loading -> LoadingScreen()
        is HomeState.Data -> ArticleListScreen(
            articles = state.items, navigateToDetail = navigateToDetail, navigateToPlayer = navigateToPlayer
        )
        HomeState.Error -> ErrorScreen()
    }
}

@Composable
fun ArticleListScreen(
    articles: List<ArticleItem>, navigateToDetail: (ArticleItem.StoryItem) -> Unit, navigateToPlayer: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = articles, key = { article ->
            // Return a stable + unique key for the item
            article.id
        }) {
            ArticleItemView(it, navigateToDetail, navigateToPlayer)
        }
    }
}

@Composable
fun ArticleItemView(
    item: ArticleItem, navigateToDetail: (ArticleItem.StoryItem) -> Unit, navigateToPlayer: (String) -> Unit
) = when (item) {
    is ArticleItem.StoryItem -> StoryItemView(item, navigateToDetail)
    is ArticleItem.VideoItem -> VideoItemView(item, navigateToPlayer)
}

@Composable
fun StoryItemView(item: ArticleItem.StoryItem, navigateToDetail: (ArticleItem.StoryItem) -> Unit) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
    shape = RoundedCornerShape(8.dp),
) {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.onPrimary)
        .clickable {
            navigateToDetail(item)
        }) {
        ThumbnailView(imageUrl = item.imageUrl, item.title)
        Text(
            text = item.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "${item.author} - ${item.duration}", style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun VideoItemView(item: ArticleItem.VideoItem, navigateToPlayer: (String) -> Unit) = Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
) {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.onPrimary)
        .clickable {
            navigateToPlayer(item.videoUrl)
        }) {
        VideoThumbnailView(item)
        Text(
            text = item.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = pluralStringResource(R.plurals.views, item.views, item.views), style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun VideoThumbnailView(item: ArticleItem.VideoItem) = Box {
    ThumbnailView(imageUrl = item.imageUrl, item.title)
    SportText(
        item.sport, modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(horizontal = 8.dp)
    )
    Image(
        painter = painterResource(R.drawable.ic_play),
        contentDescription = stringResource(R.string.play),
        modifier = Modifier.align(Alignment.Center)
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ThumbnailView(imageUrl: String, contentDescription: String) = GlideImage(
    model = imageUrl,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .aspectRatio(16f / 9f)
        .padding(bottom = 8.dp)
        .fillMaxWidth(),
    contentDescription = contentDescription
)

@Composable
@Preview
fun ThumbnailViewPreview() = ThumbnailView("url", "image")

@Composable
fun SportText(
    sport: String, modifier: Modifier = Modifier
) = Box(
    modifier = modifier
        .clip(shape = RoundedCornerShape(8.dp))
        .background(color = MaterialTheme.colorScheme.primary),
    contentAlignment = Alignment.Center,
) {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
        text = sport,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White
    )
}
