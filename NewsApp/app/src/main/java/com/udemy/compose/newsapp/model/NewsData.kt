package com.udemy.compose.newsapp.model

import com.udemy.compose.newsapp.R

data class NewsData(
    val id: Int,
    val image: Int = R.drawable.breaking_news,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)