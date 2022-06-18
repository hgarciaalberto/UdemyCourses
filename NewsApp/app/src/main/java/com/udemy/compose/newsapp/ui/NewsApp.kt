package com.udemy.compose.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udemy.compose.newsapp.coponents.BottomMenu
import com.udemy.compose.newsapp.model.TopNewsArticle
import com.udemy.compose.newsapp.network.NewsManager
import com.udemy.compose.newsapp.ui.screen.Categories
import com.udemy.compose.newsapp.ui.screen.DetailScreen
import com.udemy.compose.newsapp.ui.screen.Sources
import com.udemy.compose.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController, scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(
        bottomBar = {
            BottomMenu(navController = navController)
        },
    ) { paddingValues ->
        Navigation(navController = navController, scrollState = scrollState, paddingValues = paddingValues)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {

    val articles = mutableListOf(TopNewsArticle())
    articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))

    Log.d("news", "articles: $articles")

    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        bottomNavigation(navController, articles, newsManager)

        composable(
            route = "Detail/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            articles.clear()
            if (newsManager.query.value.isNotEmpty()) {
                articles.addAll(newsManager.getSearchedNewsResponse.value.articles ?: listOf(TopNewsArticle()))
            } else {
                articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))
            }

            index?.let {
                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticle>,
    newsManager: NewsManager
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles = articles,
            query = newsManager.query,
            newsManager = newsManager
        )
    }
    composable(BottomMenuScreen.Categories.route) {
        newsManager.getArticlesByCategory("business")
        newsManager.onSelectedCategoryChanged("business")

        Categories(
            newsManager = newsManager,
            onFetchCategory = {
                newsManager.onSelectedCategoryChanged(it)
                newsManager.getArticlesByCategory(it)
            }
        )
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager = newsManager)
    }
}