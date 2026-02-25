package com.example.newsappcompose.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsappcompose.data.model.Article
import com.example.newsappcompose.data.model.Source
import com.example.newsappcompose.data.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NewsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        repository = mock()
    }

    @Test
    fun `loadNews should update news on success`() = runTest(testDispatcher) {
        val articles = listOf(
            createArticle("1", "Title 1"),
            createArticle("2", "Title 2")
        )

        whenever(repository.getTopHeadlines()).thenReturn(Result.success(articles))

        viewModel = NewsViewModel(repository)
        viewModel.loadNews()
        advanceUntilIdle()

        assertEquals(2, viewModel.news.size)
        assertEquals("Title 1", viewModel.news[0].title)
        assertFalse(viewModel.isLoading)
        assertNull(viewModel.error)
    }

    @Test
    fun `loadNews should update error on failure`() = runTest(testDispatcher) {
        val errorMessage = "Network error"
        whenever(repository.getTopHeadlines()).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel = NewsViewModel(repository)
        viewModel.loadNews()
        advanceUntilIdle()

        assertEquals(errorMessage, viewModel.error)
        assertTrue(viewModel.news.isEmpty())
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun `retry should reload news`() = runTest(testDispatcher) {
        val articles = listOf(createArticle("1", "Title 1"))
        whenever(repository.getTopHeadlines()).thenReturn(Result.success(articles))

        viewModel = NewsViewModel(repository)
        viewModel.retry()
        advanceUntilIdle()

        assertEquals(1, viewModel.news.size)
    }

    @Test
    fun `loadNews should handle empty list`() = runTest(testDispatcher) {
        whenever(repository.getTopHeadlines()).thenReturn(Result.success(emptyList()))

        viewModel = NewsViewModel(repository)
        viewModel.loadNews()
        advanceUntilIdle()

        assertTrue(viewModel.news.isEmpty())
        assertFalse(viewModel.isLoading)
        assertNull(viewModel.error)
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
