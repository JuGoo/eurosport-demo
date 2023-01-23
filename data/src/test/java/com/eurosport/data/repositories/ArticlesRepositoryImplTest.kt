package com.eurosport.data.repositories

import com.eurosport.data.model.ArticlesResponse
import com.eurosport.data.model.SportResponse
import com.eurosport.data.model.StoryResponse
import com.eurosport.data.model.VideoResponse
import com.eurosport.data.services.EurosportService
import com.eurosport.domain.model.Article
import com.eurosport.domain.model.Sport
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticlesRepositoryImplTest {

    @MockK
    private lateinit var service: EurosportService

    private lateinit var repository: ArticlesRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = ArticlesRepositoryImpl(service, UnconfinedTestDispatcher())
    }

    @Test
    fun `when service return empty response`() = runTest {
        // given
        coEvery { service.fetchArticles() } returns ArticlesResponse()
        // when
        val result = repository.fetchArticles()
        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `when service return nominal response`() = runTest {
        // given
        val response = createArticlesResponse(1, 2, 3, 4, 5)
        coEvery { service.fetchArticles() } returns response
        // when
        val result = repository.fetchArticles()
        // then
        val expected: List<Article> = createDummyArticles(1, 2, 3, 4, 5)
        assertThat(result).isEqualTo(expected)
    }
}

private fun createArticlesResponse(vararg ids: Int): ArticlesResponse = ArticlesResponse(
    videos = createVideos(ids),
    stories = createStories(ids)
)

private fun createVideos(ids: IntArray): List<VideoResponse> = ids.map {
    VideoResponse(
        id = it.toLong(),
        title = it.toString(),
        thumb = it.toString(),
        url = it.toString(),
        date = null,
        sport = SportResponse(0, "foot"),
        views = null
    )
}

private fun createStories(ids: IntArray): List<StoryResponse> = ids.map {
    StoryResponse(
        id = it.toLong(),
        title = it.toString(),
        image = it.toString(),
        teaser = null,
        date = null,
        author = null,
        sport = SportResponse(0, "foot")
    )
}

private fun createDummyArticles(vararg ids: Int): List<Article> = ids.map {
    Article.Video(
        id = it.toLong(),
        title = it.toString(),
        imageUrl = it.toString(),
        date = null,
        sport = Sport(id = 0, name = "foot"),
        views = 0,
        url = it.toString()
    )
} + ids.map {
    Article.Story(
        id = it.toLong(),
        title = it.toString(),
        imageUrl = it.toString(),
        date = null,
        sport = Sport(id = 0, name = "foot"),
        teaser = null,
        author = null
    )
}
