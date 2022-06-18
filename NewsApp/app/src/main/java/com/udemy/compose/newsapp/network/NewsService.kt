package com.udemy.compose.newsapp.network

import com.udemy.compose.newsapp.model.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(
        @Query("country") country: String
    ): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategory(
        @Query("category") category: String
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getArticlesBySource(
        @Query("sources") sources: String
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getArticles(
        @Query("q") sources: String
    ): Call<TopNewsResponse>

}
