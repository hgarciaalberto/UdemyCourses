package com.udemy.compose.newsapp.network

import com.udemy.compose.newsapp.data.model.TopNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country") country: String
    ): TopNewsResponse

    @GET("top-headlines")
    suspend fun getArticlesByCategory(
        @Query("category") category: String
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticlesBySource(
        @Query("sources") sources: String
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticles(
        @Query("q") sources: String
    ): TopNewsResponse

}
