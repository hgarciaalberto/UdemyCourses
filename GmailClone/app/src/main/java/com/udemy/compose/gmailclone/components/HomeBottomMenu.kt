package com.udemy.compose.gmailclone.components

import android.widget.Toast
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.udemy.compose.gmailclone.model.BottomMenuData

@Composable
fun HomeBottomMenu() {
    val context = LocalContext.current

    val items = listOf(
        BottomMenuData.Mail,
        BottomMenuData.Meet,
    )
    BottomNavigation(backgroundColor = Color.White, contentColor = Color.Black) {
        items.forEach {
            BottomNavigationItem(
                label = {
                    Text(text = it.title)
                },
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    Toast.makeText(context, "clicked: ${it.title}", Toast.LENGTH_SHORT).show()
                },
                icon = { Icon(it.icon, contentDescription = it.title) }
            )
        }

    }

}