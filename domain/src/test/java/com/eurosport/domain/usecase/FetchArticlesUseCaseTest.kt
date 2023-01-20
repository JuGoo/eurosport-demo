package com.eurosport.domain.usecase

import com.eurosport.domain.model.Article
import com.eurosport.domain.model.Sport
import com.eurosport.domain.repositories.ArticlesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import com.google.common.truth.Truth.assertThat
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchArticlesMixedUseCaseTest {

    @MockK
    private lateinit var repository: ArticlesRepository

    private lateinit var fetchArticlesMixedUseCase: FetchArticlesMixedUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        fetchArticlesMixedUseCase = FetchArticlesMixedUseCase(repository)
    }

    @Test
    fun `when repo return empty result`() = runTest {
        coEvery { repository.fetchArticles() } returns emptyList()
        val result = fetchArticlesMixedUseCase.run()
        assertThat(result).isEqualTo(Result.success(emptyList<Article>()))
    }

    @Test
    fun `nominal case`() = runTest {
        val dummyVideos = createDummyVideos(listOf(1, 2, 3, 4, 5))
        val dummyStories = createDummyStories(listOf(1, 2, 3))
        coEvery { repository.fetchArticles() } returns (dummyVideos + dummyStories)
        val result = fetchArticlesMixedUseCase.run()
        assertThat(result).isEqualTo(Result.success(expected))
    }

    @Test
    fun `when repo throw`() = runTest {
        val exception: Throwable = IllegalStateException()
        coEvery { repository.fetchArticles() } throws exception
        val result = fetchArticlesMixedUseCase.run()
        assertThat(result.isFailure).isTrue()
    }
}

private val expected: List<Article> = createDummyStories(listOf(1)) + createDummyVideos(listOf(1)) +
        createDummyStories(listOf(2)) + createDummyVideos(listOf(2)) +
        createDummyStories(listOf(3)) + createDummyVideos(listOf(3)) +
        createDummyVideos(listOf(4)) + createDummyVideos(listOf(5))

private fun createDummyVideos(ids: List<Int>) = ids.map {
    Article.Video(
        id = it.toLong(),
        title = it.toString(),
        imageUrl = null,
        date = null,
        sport = Sport(id = 1, name = "Football"),
        url = "videoUrl",
        views = 0
    )
}

private fun createDummyStories(ids: List<Int>) = ids.map {
    Article.Story(
        id = it.toLong(),
        title = it.toString(),
        imageUrl = null,
        date = null,
        sport = Sport(id = 3, name = "Handball"),
        teaser = "Incroyable match",
        author = "Toto"
    )
}