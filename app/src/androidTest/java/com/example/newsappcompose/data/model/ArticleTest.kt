package com.example.newsappcompose.data.model

import android.os.Parcel
import org.junit.Assert.*
import org.junit.Test

class ArticleTest {

    @Test
    fun `article should be parcelable`() {
        val article = Article(
            source = Source(id = "test-id", name = "Test Source"),
            author = "Test Author",
            title = "Test Title",
            description = "Test Description",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = "2024-01-01",
            content = "Test Content"
        )

        val parcel = Parcel.obtain()
        article.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val createdFromParcel = Article.CREATOR.createFromParcel(parcel)
        parcel.recycle()

        assertEquals(article.title, createdFromParcel.title)
        assertEquals(article.description, createdFromParcel.description)
        assertEquals(article.url, createdFromParcel.url)
    }

    @Test
    fun `article with null values should be parcelable`() {
        val article = Article(
            source = null,
            author = null,
            title = null,
            description = null,
            url = null,
            urlToImage = null,
            publishedAt = null,
            content = null
        )

        val parcel = Parcel.obtain()
        article.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val createdFromParcel = Article.CREATOR.createFromParcel(parcel)
        parcel.recycle()

        assertNull(createdFromParcel.title)
        assertNull(createdFromParcel.description)
    }

    @Test
    fun `source should be parcelable`() {
        val source = Source(id = "test-id", name = "Test Source")

        val parcel = Parcel.obtain()
        source.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val createdFromParcel = Source.CREATOR.createFromParcel(parcel)
        parcel.recycle()

        assertEquals(source.id, createdFromParcel.id)
        assertEquals(source.name, createdFromParcel.name)
    }
}
