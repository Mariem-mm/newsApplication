package com.example.newsappcompose.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    @SerializedName("source")
    val source: Source? = null,
    
    @SerializedName("author")
    val author: String? = null,
    
    @SerializedName("title")
    val title: String? = null,
    
    @SerializedName("description")
    val description: String? = null,
    
    @SerializedName("url")
    val url: String? = null,
    
    @SerializedName("urlToImage")
    val urlToImage: String? = null,
    
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    
    @SerializedName("content")
    val content: String? = null
) : Parcelable

@Parcelize
data class Source(
    @SerializedName("id")
    val id: String? = null,
    
    @SerializedName("name")
    val name: String? = null
) : Parcelable
