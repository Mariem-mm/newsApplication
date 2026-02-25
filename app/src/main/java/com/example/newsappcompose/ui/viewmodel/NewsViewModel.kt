package com.example.newsappcompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.data.model.Article
import com.example.newsappcompose.data.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    
    var news by mutableStateOf<List<Article>>(emptyList())
        private set
    
    var isLoading by mutableStateOf(false)
        private set
    
    var error by mutableStateOf<String?>(null)
        private set
    
    init {
        loadNews()
    }
    
    fun loadNews() {
        viewModelScope.launch {
            isLoading = true
            error = null
            
            newsRepository.getTopHeadlines()
                .onSuccess { articles ->
                    news = articles
                    isLoading = false
                }
                .onFailure { exception ->
                    error = exception.message ?: "Erreur inconnue"
                    isLoading = false
                }
        }
    }
    
    fun retry() {
        loadNews()
    }
}
