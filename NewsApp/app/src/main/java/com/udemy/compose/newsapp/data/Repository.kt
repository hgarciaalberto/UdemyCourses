package com.udemy.compose.newsapp.data

import com.udemy.compose.newsapp.network.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source: String) = manager.getArticlesBySource(source)
    suspend fun getSearchedArticles(query:String) = manager.getSearchArticles(query)
}