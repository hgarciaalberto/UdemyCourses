package com.udemy.compose.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.udemy.compose.newsapp.R
import com.udemy.compose.newsapp.data.model.TopNewsArticle
import com.udemy.compose.newsapp.ui.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sources(viewModel: MainViewModel) {

    val items = listOf(
        "TechCrunch" to "techcrunch",
        "News" to "abc-news",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "${viewModel.sourceName.collectAsState().value} Source"
                )
            },
            actions = {
                var menuExpanded by remember { mutableStateOf(false) }
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        items.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.sourceName.value = it.second
                                    viewModel.getArticlesBySource()
                                    menuExpanded = false
                                }
                            ) {
                                Text(it.first)
                            }
                        }
                    }
                }
            }
        )
    }) {
        viewModel.getArticlesBySource()
        val article = viewModel.getArticleBySource.collectAsState().value
        SourceContent(articles = article.articles ?: listOf())
    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticle>) {
    val uriHandler = LocalUriHandler.current

    LazyColumn {
        items(articles) { article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(id = R.color.purple_500),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Read Full Article Here")
                }
                pop()
            }
            Card(
                backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp, modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(backgroundColor = Color.White, elevation = 6.dp) {
                        ClickableText(text = annotatedString, onClick = {
                            annotatedString.getStringAnnotations(it, it).firstOrNull()?.let { result ->
                                if (result.tag == "URL") {
                                    uriHandler.openUri(result.item)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SourceContentPreview() {
    SourceContent(
        articles = listOf(
            TopNewsArticle(
                author = "Namita Singh",
                title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
                description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
                publishedAt = "2021-11-04T04:42:40Z"
            )
        )
    )
}