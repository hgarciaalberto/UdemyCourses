package com.udemy.compose.gmailclone.components

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun GmailFab(scrollState: ScrollState) {
    val context = LocalContext.current

    if (scrollState.value != 0) {
        ExtendedFloatingActionButton(
            text = {
                Text("Compose", fontSize = 16.sp)
            },
            onClick = {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            },
            icon = {
                Icon(imageVector = Icons.Default.Edit, "")
            },
            backgroundColor = MaterialTheme.colors.surface
        )
    } else {
        FloatingActionButton(
            onClick = {
                Toast.makeText(context, "FAB clicked", Toast.LENGTH_SHORT).show()
            },
            backgroundColor = MaterialTheme.colors.surface,
        ) {
            Icon(imageVector = Icons.Default.Edit, "")
        }
    }
}