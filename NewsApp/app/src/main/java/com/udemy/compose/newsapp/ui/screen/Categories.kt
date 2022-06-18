package com.udemy.compose.newsapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.udemy.compose.newsapp.R
import com.udemy.compose.newsapp.coponents.ErrorUI
import com.udemy.compose.newsapp.coponents.LoadingUI
import com.udemy.compose.newsapp.data.model.MockData
import com.udemy.compose.newsapp.data.model.MockData.getTimeAgo
import com.udemy.compose.newsapp.data.model.TopNewsArticle
import com.udemy.compose.newsapp.data.model.getAllArticlesCategories
import com.udemy.compose.newsapp.ui.MainViewModel

@Composable
fun Categories(
    onFetchCategory: (String) -> Unit = {},
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    val tabsItems = getAllArticlesCategories()

    Column {
        when {
            isLoading.value -> LoadingUI()

            isError.value -> ErrorUI()

            else -> {
                LazyRow {
                    items(tabsItems.size) {
                        val category = tabsItems[it]
                        CategoryTab(
                            category = category.categoryName,
                            onFetchCategory = onFetchCategory,
                            isSelected = viewModel.selectedCategory.collectAsState().value == category
                        )
                    }
                }
                ArticlesContent(articles = viewModel.getArticleByCategory.collectAsState().value.articles ?: listOf())
            }
        }
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean = false, onFetchCategory: (String) -> Unit) {
    val background = if (isSelected) {
        colorResource(R.color.purple_200)
    } else {
        colorResource(R.color.purple_700)
    }

    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ArticlesContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LazyColumn {
        items(articles) { article ->
            Card(modifier.padding(8.dp), border = BorderStroke(2.dp, color = colorResource(R.color.purple_500))) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier = Modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.breaking_news),
                        error = painterResource(id = R.drawable.breaking_news)
                    )
                    Column(modifier.padding(8.dp)) {
                        Text(
                            text = article.title ?: "Not Available",
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(text = article.author ?: "Not Available")
                        Text(
                            text = MockData.stringToDate(
                                article.publishedAt ?: "2020-01-01T00:00:00Z"
                            )!!.getTimeAgo(context)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleContentPreview() {
    ArticlesContent(
        articles = listOf(
            TopNewsArticle(
                author = "Nanita Singh",
                title = "Cleo Smith: 'I'm not a fan of the new iPhone'",
                description = "The new iPhone is a great device, but it's a bit of a pain to get it. Cleo Smith is a...",
                publishedAt = "2020-01-01T00:00:00Z"
            )
        )
    )
}