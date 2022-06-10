package com.udemy.compose.newsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udemy.compose.newsapp.coponents.BottomMenu
import com.udemy.compose.newsapp.model.MockData
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(
        bottomBar = {
            BottomMenu(navController = navController)
        },
    ) {
        Navigation(navController = navController, scrollState = scrollState)
    }
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState) {

    NavHost(navController = navController, startDestination = "TopNews") {
        bottomNavigation(navController)
        composable("TopNews") {
            TopNews(navController = navController)
        }
        composable(
            route = "Detail/{newsId}",
            arguments = listOf(navArgument("newsId") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId") ?: 0
            val newsData = MockData.getNews(id)
            DetailScreen(newsData, scrollState, navController)
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories(/*navController = navController*/)
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(/*navController = navController*/)
    }
}