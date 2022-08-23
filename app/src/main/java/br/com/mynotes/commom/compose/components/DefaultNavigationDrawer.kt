package br.com.mynotes.commom.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.mynotes.features.notes.presentation.model.MenuItem
import br.com.mynotes.R
import br.com.mynotes.features.notes.presentation.screens.home.ScreenState

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.app_name), fontSize = 52.sp, style = TextStyle(fontStyle = FontStyle.Italic))
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
    currentRoute: ScreenState
) {
    LazyColumn {
        items(items) { item ->
            val isEnable = item.screen != currentRoute
            val contentColor = MaterialTheme.colors.let { color ->
                if (isEnable) contentColorFor(color.background) else color.primary
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = isEnable) {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = contentColor
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f),
                    color = contentColor
                )
            }
        }
    }
}