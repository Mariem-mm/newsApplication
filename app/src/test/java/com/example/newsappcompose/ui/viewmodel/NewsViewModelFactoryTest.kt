package com.example.newsappcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsappcompose.data.repository.NewsRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class NewsViewModelFactoryTest {

    private lateinit var repository: NewsRepository
    private lateinit var factory: NewsViewModelFactory

    @Before
    fun setup() {
        repository = mock()
        factory = NewsViewModelFactory(repository)
    }

    @Test
    fun `create should return NewsViewModel for correct class`() {
        val viewModel = factory.create(NewsViewModel::class.java)

        assertNotNull(viewModel)
        assertTrue(viewModel is NewsViewModel)
    }

    @Test
    fun `create should throw exception for unknown class`() {
        try {
            factory.create(TestViewModel::class.java)
            fail("Should throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertEquals("Unknown ViewModel class", e.message)
        }
    }

    private class TestViewModel : ViewModel()
}
