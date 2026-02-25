package com.example.newsappcompose.data.repository

import com.example.newsappcompose.data.api.NewsApiService
import com.example.newsappcompose.data.model.Article
import com.example.newsappcompose.data.model.NewsResponse
import com.example.newsappcompose.data.model.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryTest {

    private lateinit var apiService: NewsApiService
    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        apiService = mock()
        repository = NewsRepository(apiService, null)
    }

    @Test
    fun `getTopHeadlines should return articles on successful response`() = runTest {
        val articles = listOf(
            createArticle("1", "Title 1"),
            createArticle("2", "Title 2")
        )
        val response = Response.success(NewsResponse(status = "ok", totalResults = 2, articles = articles))

        whenever(apiService.getTopHeadlines(any(), any())).thenReturn(response)

        val result = repository.getTopHeadlines()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Title 1", result.getOrNull()?.get(0)?.title)
    }

    @Test
    fun `getTopHeadlines should return failure on unsuccessful response`() = runTest {
        val response = Response.error<NewsResponse>(
            500,
            "Server Error".toResponseBody("application/json".toMediaType())
        )

        whenever(apiService.getTopHeadlines(any(), any())).thenReturn(response)

        val result = repository.getTopHeadlines()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Erreur API") == true)
    }

    @Test
    fun `getTopHeadlines should return failure on null response body`() = runTest {
        val response = Response.success<NewsResponse>(null)

        whenever(apiService.getTopHeadlines(any(), any())).thenReturn(response)

        val result = repository.getTopHeadlines()

        assertTrue(result.isFailure)
    }

    @Test
    fun `getTopHeadlines should return failure on exception`() = runTest {
        whenever(apiService.getTopHeadlines(org.mockito.kotlin.any(), org.mockito.kotlin.any())).thenThrow(RuntimeException("Network error"))

        val result = repository.getTopHeadlines()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getTopHeadlines should handle empty articles list`() = runTest {
        val response = Response.success(NewsResponse(status = "ok", totalResults = 0, articles = emptyList()))

        whenever(apiService.getTopHeadlines(any(), any())).thenReturn(response)

        val result = repository.getTopHeadlines()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    private fun createArticle(id: String, title: String): Article {
        return Article(
            source = Source(id = id, name = "Source $id"),
            title = title,
            description = "Description $id",
            url = "https://example.com/$id",
            urlToImage = "https://example.com/image$id.jpg"
        )
    }
}
