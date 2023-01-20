package com.eurosport.data.repositories

import com.eurosport.data.model.StoryResponse
import com.eurosport.data.model.VideoResponse
import com.eurosport.domain.model.Article
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ArticlesTest {

    @Test
    fun `map empty list of video response`() {
        val result = emptyList<VideoResponse>().mapToVideos()
        assertThat(result).isEqualTo(emptyList<Article.Video>())
    }

    @Test
    fun `map empty list of story response`() {
        val result = emptyList<StoryResponse>().mapToStories()
        assertThat(result).isEqualTo(emptyList<Article.Story>())
    }

    @Test
    fun `map list of story response and ignore story dont respect mandatory fields`() {
        val result = listOf(
            StoryResponse(id = 1, title = "title 1", image = "image 1"),
            StoryResponse(id = null, title = "title 2", image = "image 2"),
            StoryResponse(id = 3, title = null, image = "image 3"),
            StoryResponse(id = 4, title = "title 4", image = null),
        ).mapToStories()
        val expected = listOf(
            Article.Story(1, "title 1"),
            Article.Story(4, "title 4")
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `map list of video response and ignore story dont respect mandatory fields`() {
        val result = listOf(
            VideoResponse(id = 1, title = "title 1", thumb = "image 1"),
            VideoResponse(id = null, title = "title 2", thumb = "image 2"),
            VideoResponse(id = 3, title = null, thumb = "image 3"),
            VideoResponse(id = 4, title = "title 4", thumb = null)
        ).mapToVideos()
        val expected = listOf(
            Article.Video(1, "title 1"),
            Article.Video(4, "title 4")
        )
        assertThat(result).isEqualTo(expected)
    }
}