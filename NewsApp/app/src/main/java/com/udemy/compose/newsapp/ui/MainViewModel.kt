package com.udemy.compose.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.compose.newsapp.MainApp
import com.udemy.compose.newsapp.data.model.ArticleCategory
import com.udemy.compose.newsapp.data.model.TopNewsResponse
import com.udemy.compose.newsapp.data.model.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse> = _newsResponse

    private val _getArticleByCategory = MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse> = _getArticleByCategory

    private val _selectedCategory = MutableStateFlow<ArticleCategory?>(null)
    val selectedCategory: StateFlow<ArticleCategory?> = _selectedCategory

    val sourceName = MutableStateFlow("engadget")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse> = _getArticleBySource

    val query = MutableStateFlow("") // why??
    private val _searchedNewsResponse = MutableStateFlow(TopNewsResponse())
    val searchedNewsResponse: StateFlow<TopNewsResponse> = _searchedNewsResponse


    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getTopArticles() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleByCategory.value = repository.getArticlesByCategory(category)
        }
        _isLoading.value = false
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    fun getArticlesBySource() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
        }
        _isLoading.value = false
    }

    fun getSearchedArticles(query: String){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _searchedNewsResponse.value = repository.getSearchedArticles(query)
        }
        _isLoading.value = false
    }
}