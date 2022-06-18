package com.udemy.compose.newsapp.network

import com.udemy.compose.newsapp.data.model.TopNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsManager(private val service: NewsService) {

    suspend fun getArticles(country: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(country = country)
    }

    suspend fun getArticlesByCategory(category: String) = withContext(Dispatchers.IO) {
        service.getArticlesByCategory(category)
    }

    suspend fun getArticlesBySource(source: String) = withContext(Dispatchers.IO) {
        service.getArticlesBySource(source)
    }

    suspend fun getSearchArticles(query: String) = withContext(Dispatchers.IO) {
        service.getArticles(query)
    }
}