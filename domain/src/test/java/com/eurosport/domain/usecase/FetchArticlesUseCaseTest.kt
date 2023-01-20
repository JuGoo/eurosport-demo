package com.eurosport.domain.usecase

import com.eurosport.domain.model.Article
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
class FetchArticlesUseCaseTest {

    @MockK
    private lateinit var repository: ArticlesRepository

    private lateinit var fetchArticlesUseCase: FetchArticlesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        fetchArticlesUseCase = FetchArticlesUseCase(repository)
    }

    @Test
    fun `when repo return empty result`() = runTest {
        coEvery { repository.fetchArticles() } returns emptyList()
        val result = fetchArticlesUseCase.run()
        assertThat(result).isEqualTo(Result.success(emptyList<Article>()))
    }

    @Test
    fun `nominal case`() = runTest {
        val dummyArticles = createDummyArticles(1, 2, 3, 4, 5)
        coEvery { repository.fetchArticles() } returns dummyArticles
        val result = fetchArticlesUseCase.run()
        assertThat(result).isEqualTo(Result.success(dummyArticles))
    }

    @Test
    fun `when repo throw`() = runTest {
        val exception: Throwable = IllegalStateException()
        coEvery { repository.fetchArticles() } throws exception
        val result = fetchArticlesUseCase.run()
        assertThat(result.isFailure).isTrue()
    }
}

private fun createDummyArticles(vararg ids: Int): List<Article> = ids.map {
    Article.Video(id = it.toLong(), title = it.toString())
} + ids.map {
    Article.Story(id = it.toLong(), title = it.toString())
}