package com.example.newsappcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsappcompose.data.api.RetrofitClient
import com.example.newsappcompose.data.model.Article
import com.example.newsappcompose.data.repository.NewsRepository
import com.example.newsappcompose.ui.newsdetail.NewsDetailScreen
import com.example.newsappcompose.ui.newslist.NewsListScreen
import com.example.newsappcompose.ui.viewmodel.NewsViewModel
import com.example.newsappcompose.ui.viewmodel.NewsViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    
    NavHost(
        navController = navController,
        startDestination = "news_list"
    ) {
        composable("news_list") {
            val viewModel: NewsViewModel = viewModel(
                factory = NewsViewModelFactory(
                    NewsRepository(RetrofitClient.newsApiService, context)
                )
            )
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    selectedArticle = article
                    navController.navigate("news_detail")
                }
            )
        }
        
        composable("news_detail") {
            selectedArticle?.let { article ->
                NewsDetailScreen(
                    article = article,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
