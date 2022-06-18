package com.udemy.compose.newsapp.data.model

import com.udemy.compose.newsapp.data.model.ArticleCategory.BUSINESS
import com.udemy.compose.newsapp.data.model.ArticleCategory.ENTERTAINMENT
import com.udemy.compose.newsapp.data.model.ArticleCategory.GENERAL
import com.udemy.compose.newsapp.data.model.ArticleCategory.HEALTH
import com.udemy.compose.newsapp.data.model.ArticleCategory.SCIENCE
import com.udemy.compose.newsapp.data.model.ArticleCategory.SPORTS
import com.udemy.compose.newsapp.data.model.ArticleCategory.TECHNOLOGY

enum class ArticleCategory(val categoryName: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

fun getAllArticlesCategories(): List<ArticleCategory> {
    return listOf(
        BUSINESS,
        ENTERTAINMENT,
        GENERAL,
        HEALTH,
        SCIENCE,
        SPORTS,
        TECHNOLOGY
    )
}

fun getArticleCategory(category: String): ArticleCategory? {
    val map = ArticleCategory.values().associateBy(ArticleCategory::categoryName)
    return map[category]
}