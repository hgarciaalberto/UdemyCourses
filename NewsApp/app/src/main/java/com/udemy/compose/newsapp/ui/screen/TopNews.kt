package com.udemy.compose.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import com.udemy.compose.newsapp.R
import com.udemy.compose.newsapp.coponents.ErrorUI
import com.udemy.compose.newsapp.coponents.LoadingUI
import com.udemy.compose.newsapp.coponents.SearchBar
import com.udemy.compose.newsapp.data.model.MockData
import com.udemy.compose.newsapp.data.model.MockData.getTimeAgo
import com.udemy.compose.newsapp.data.model.TopNewsArticle
import com.udemy.compose.newsapp.ui.MainViewModel

@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(query, viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        if (searchedText.isNotBlank()) {
            resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles ?: articles)
        } else {
            resultList.addAll(articles)
        }

        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                LazyColumn {
                    items(articles.size) { index ->
                        TopNewsItem(
                            article = resultList[index],
                            onNewsClicked = { navController.navigate("Detail/$index") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClicked: () -> Unit = {}) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable { onNewsClicked() }
    ) {
        CoilImage(
            imageModel = article.urlToImage,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
            placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            article.publishedAt?.let {
                Text(
                    text = MockData.stringToDate(it)!!.getTimeAgo(context),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            article.title?.let {
                Text(text = it, color = Color.White, fontWeight = FontWeight.SemiBold)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(
        TopNewsArticle(
            author = "Nanita Singh",
            title = "Cleo Smith: 'I'm not a fan of the new iPhone'",
            description = "The new iPhone is a great device, but it's a bit of a pain to get it. Cleo Smith is a...",
            publishedAt = "2020-01-01T00:00:00Z"
        )
    )
}