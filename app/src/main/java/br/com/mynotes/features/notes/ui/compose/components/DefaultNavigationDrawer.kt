package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.model.MenuItem
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .padding(vertical = 64.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 52.sp,
            style = TextStyle(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBody(
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
    currentRoute: ScreenState
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(items) { item ->
            val isSelected = item.screen == currentRoute
            NavigationDrawerItem(
                modifier = Modifier.padding(vertical = 4.dp),
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onSecondaryContainer
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                selected = isSelected,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        tint = if (isSelected)
                            MaterialTheme.colorScheme.onSecondaryContainer
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null
                    )
                },
                onClick = {
                    onItemClick(item)
                }
            )
        }
    }
}