package com.udemy.compose.newsapp

import android.app.Application
import com.udemy.compose.newsapp.data.Repository
import com.udemy.compose.newsapp.network.Api
import com.udemy.compose.newsapp.network.NewsManager

class MainApp : Application() {
    private val manager by lazy { NewsManager(Api.retrofitService) }
    val repository by lazy { Repository(manager) }
}