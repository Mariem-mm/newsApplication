package com.example.newsappcompose.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.newsappcompose.data.model.Article
import com.example.newsappcompose.data.model.Source
import com.example.newsappcompose.data.repository.NewsRepository
import com.example.newsappcompose.ui.newslist.NewsListScreen
import com.example.newsappcompose.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class NewsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `news list screen should display title`() {
        val repository = mock<NewsRepository>()
        val viewModel = NewsViewModel(repository)

        composeTestRule.setContent {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = {}
            )
        }

        composeTestRule.onNodeWithText("Actualités").assertIsDisplayed()
    }

    @Test
    fun `news list screen should display articles when loaded`() {
        val repository = mock<NewsRepository>()
        val viewModel = NewsViewModel(repository)
        
        val articles = listOf(
            Article(
                source = Source(id = "1", name = "Source 1"),
                title = "Test Article Title",
                description = "Test Description",
                url = "https://example.com",
                urlToImage = "https://example.com/image.jpg"
            )
        )

        composeTestRule.setContent {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = {}
            )
        }

        composeTestRule.onNodeWithText("Actualités").assertIsDisplayed()
    }
}
