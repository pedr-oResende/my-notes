package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.model.MenuItem
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens

@Composable
fun DefaultNavigationDrawer(
    onItemClick: (MenuItem) -> Unit,
    currentScreen: DrawerScreens
) {
    val items = listOf(
        MenuItem(
            screen = DrawerScreens.Home,
            title = stringResource(R.string.menu_item_home),
            icon = Icons.Outlined.Home
        ),
        MenuItem(
            screen = DrawerScreens.Archive,
            title = stringResource(R.string.menu_item_archive),
            icon = Icons.Outlined.Archive
        ),
        MenuItem(
            screen = DrawerScreens.TrashCan,
            title = stringResource(R.string.menu_item_trash_can),
            icon = Icons.Outlined.Delete
        ),
    )
    DrawerHeader()
    DrawerBody(
        items = items,
        onItemClick = onItemClick,
        currentScreen = currentScreen
    )
}

@Composable
private fun DrawerHeader() {
    Box(modifier = Modifier.padding(all = 24.dp)) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DrawerBody(
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
    currentScreen: DrawerScreens
) {
    items.forEach { item ->
        val isSelected = item.screen == currentScreen
        NavigationDrawerItem(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
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
            onClick = { onItemClick(item) }
        )
    }
}