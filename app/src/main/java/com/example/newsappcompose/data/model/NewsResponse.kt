package com.example.newsappcompose.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    val status: String? = null,
    
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    
    @SerializedName("articles")
    val articles: List<Article> = emptyList()
)
