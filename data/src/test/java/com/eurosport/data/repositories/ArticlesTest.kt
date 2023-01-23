package com.eurosport.data.repositories

import com.eurosport.data.model.SportResponse
import com.eurosport.data.model.StoryResponse
import com.eurosport.data.model.VideoResponse
import com.eurosport.domain.model.Article
import com.eurosport.domain.model.Sport
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
            StoryResponse(
                id = 1,
                title = "title 1",
                image = "image 1",
                teaser = null,
                date = null,
                author = null,
                sport = SportResponse(1, "foot")
            ),
            StoryResponse(
                id = null,
                title = "title 2",
                image = "image 2",
                teaser = null,
                date = null,
                author = null,
                sport = null
            ),
            StoryResponse(id = 3, title = null, image = "image 3", teaser = null, date = null, author = null, sport = null),
            StoryResponse(
                id = 4,
                title = "title 4",
                image = null,
                teaser = null,
                date = null,
                author = null,
                sport = SportResponse(2, "triathlon")
            ),
        ).mapToStories()
        val expected = listOf(
            Article.Story(1, "title 1", sport = Sport(1, "foot"), imageUrl = "image 1", date = null, teaser = null, author = null),
            Article.Story(4, "title 4", sport = Sport(2, "triathlon"), imageUrl = null, date = null, teaser = null, author = null)
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `map list of video response and ignore story dont respect mandatory fields`() {
        val result = listOf(
            VideoResponse(
                id = 1,
                title = "title 1",
                thumb = "image 1",
                url = "https://www.google.fr",
                date = null,
                sport = SportResponse(1, "foot"),
                views = 0
            ),
            VideoResponse(id = null, title = "title 2", thumb = "image 2", url = "https://www.google.fr", date = null, sport = null, views = null),
            VideoResponse(id = 3, title = null, thumb = "image 3", url = "https://www.google.fr", date = null, sport = null, views = null),
            VideoResponse(
                id = 4,
                title = "title 4",
                thumb = null,
                url = "https://www.google.fr",
                date = null,
                sport = SportResponse(2, "triathlon"),
                views = 100
            )
        ).mapToVideos()
        val expected = listOf(
            Article.Video(1, "title 1", date = null, sport = Sport(1, "foot"), views = 0, imageUrl = "image 1", url = "https://www.google.fr"),
            Article.Video(4, "title 4", date = null, sport = Sport(2, "triathlon"), views = 100, imageUrl = null, url = "https://www.google.fr")
        )
        assertThat(result).isEqualTo(expected)
    }
}