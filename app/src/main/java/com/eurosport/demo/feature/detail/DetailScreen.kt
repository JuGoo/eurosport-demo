package com.eurosport.demo.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eurosport.demo.feature.home.SportText
import com.eurosport.demo.feature.home.ThumbnailView
import com.eurosport.presentation.model.ArticleItem

@Composable
fun DetailScreen(item: ArticleItem.StoryItem, popBackStack: () -> Boolean) = Column {
    Box {
        ThumbnailView(imageUrl = item.imageUrl, "item.title")
        SportText(
            item.sport, modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 8.dp)
        )
    }
    Text(
        text = item.title,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "${item.author} - ${item.duration}",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = item.teaser,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
