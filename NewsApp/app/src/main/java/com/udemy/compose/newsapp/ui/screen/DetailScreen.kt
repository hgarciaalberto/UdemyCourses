package com.udemy.compose.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.coil.CoilImage
import com.udemy.compose.newsapp.R
import com.udemy.compose.newsapp.data.model.MockData
import com.udemy.compose.newsapp.data.model.MockData.getTimeAgo
import com.udemy.compose.newsapp.data.model.TopNewsArticle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController) {
    val context = LocalContext.current

    Scaffold(topBar = {
        DetailTopAppBar(
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
            CoilImage(
                imageModel = article.urlToImage,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author ?: "Not Available")
                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt!!)!!.getTimeAgo(context)
                )
            }
            Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold)
            Text(text = article.description ?: "Not Available", Modifier.padding(16.dp))
        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(
        title = {
            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")

            }
        }
    )
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon, contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info, modifier = Modifier.align(alignment = Alignment.CenterVertically))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        TopNewsArticle(
            author = "Nanita Singh",
            title = "Cleo Smith: 'I'm not a fan of the new iPhone'",
            description = "The new iPhone is a great device, but it's a bit of a pain to get it. Cleo Smith is a...",
            publishedAt = "2020-01-01T00:00:00Z"
        ),
        rememberScrollState(),
        rememberNavController()
    )
}

