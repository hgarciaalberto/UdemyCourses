package com.udemy.compose.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import com.udemy.compose.newsapp.data.model.TopNewsArticle
import com.udemy.compose.newsapp.ui.screen.Categories
import com.udemy.compose.newsapp.ui.screen.DetailScreen
import com.udemy.compose.newsapp.ui.screen.Sources
import com.udemy.compose.newsapp.ui.screen.TopNews

@Composable
fun NewsApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController, scrollState, viewModel)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState, viewModel: MainViewModel) {
    Scaffold(
        bottomBar = {
            BottomMenu(navController = navController)
        },
    ) { paddingValues ->
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = paddingValues,
            viewModel = viewModel
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {

    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    Log.d("news", "articles: $articles")

    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        val queryState = mutableStateOf(viewModel.query.value)

        bottomNavigation(navController, articles, queryState, viewModel)

        composable(
            route = "Detail/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            articles.clear()
            if (queryState.value.isNotBlank()) {
                articles.addAll(viewModel.searchedNewsResponse.value.articles ?: listOf(TopNewsArticle()))
            } else {
                articles.addAll(viewModel.newsResponse.value.articles ?: listOf(TopNewsArticle()))
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
    query: MutableState<String>,
    viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles = articles,
            query = query,
            viewModel = viewModel
        )
    }
    composable(BottomMenuScreen.Categories.route) {
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(
            viewModel = viewModel,
            onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
            }
        )
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel)
    }
}