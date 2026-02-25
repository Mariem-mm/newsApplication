package com.example.newsappcompose.data.repository

import android.content.Context
import com.example.newsappcompose.BuildConfig
import com.example.newsappcompose.data.api.NewsApiService
import com.example.newsappcompose.data.model.Article
import com.example.newsappcompose.util.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsApiService: NewsApiService,
    private val context: Context? = null
) {
    
    suspend fun getTopHeadlines(): Result<List<Article>> = withContext(Dispatchers.IO) {
        try {
            val countryCode = LocaleHelper.getCountryCode(context)
            println("zzzzz $countryCode")
            val response = newsApiService.getTopHeadlines(
                country = countryCode,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            
            if (response.isSuccessful && response.body() != null) {
                val articles = response.body()!!.articles
                Result.success(articles)
            } else {
                Result.failure(Exception("Erreur API: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
