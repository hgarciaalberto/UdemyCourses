package com.udemy.compose.newsapp.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.udemy.compose.newsapp.model.ArticleCategory
import com.udemy.compose.newsapp.model.TopNewsResponse
import com.udemy.compose.newsapp.model.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val sourceName = mutableStateOf("abc-news")

    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory: State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val query = mutableStateOf("")

    private val _getSearchedNewsResponse = mutableStateOf(TopNewsResponse())
    val getSearchedNewsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _getSearchedNewsResponse
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getTopArticles("us")
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful) {
                    _newsResponse.value = response.body()!!
                    Log.d("news", "${_newsResponse.value}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("error", "Retrofit error", t)
            }
        })
    }

    fun getArticlesByCategory(category: String) {
        val service = Api.retrofitService.getArticlesByCategory(category)
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful) {
                    _getArticleByCategory.value = response.body()!!
                    Log.d("category", "${_getArticleByCategory.value}")
                } else {
                    Log.e("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("error", "Retrofit error", t)
            }
        })
    }

    fun getArticlesBySource() {
        val service = Api.retrofitService.getArticlesBySource(sourceName.value)
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful) {
                    _getArticleBySource.value = response.body()!!
                    Log.d("source", "${_getArticleBySource.value}")
                } else {
                    Log.e("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("error", "Retrofit error", t)
            }
        })
    }

    fun getSearchArticles(query:String) {
        val service = Api.retrofitService.getArticles(query)
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful) {
                    _getSearchedNewsResponse.value = response.body()!!
                    Log.d("search article", "${_getSearchedNewsResponse.value}")
                } else {
                    Log.e("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("error", "Retrofit error", t)
            }
        })
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}