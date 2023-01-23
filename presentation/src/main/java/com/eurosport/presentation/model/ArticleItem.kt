package com.eurosport.presentation.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurosport.domain.model.Article
import com.eurosport.domain.usecase.FetchArticlesMixedUseCase
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale

sealed class ArticleItem {
    abstract val id: String
    abstract val title: String
    abstract val sport: String
    abstract val imageUrl: String

    data class VideoItem(
        override val id: String,
        override val title: String,
        override val sport: String,
        override val imageUrl: String,
        val videoUrl: String,
        val views: Int
    ) : ArticleItem()

    @Parcelize
    data class StoryItem(
        override val id: String,
        override val title: String,
        override val sport: String,
        override val imageUrl: String,
        val author: String,
        val duration: String,
        val teaser: String
    ) : ArticleItem(), Parcelable
}
