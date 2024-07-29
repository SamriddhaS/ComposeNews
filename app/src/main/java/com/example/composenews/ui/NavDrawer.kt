package com.example.composenews.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumberedRtl
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenews.R
import com.example.composenews.ui.CompNewsDestinations.HOME_ROUTE
import com.example.composenews.ui.CompNewsDestinations.INTEREST_ROUTE
import com.example.composenews.ui.theme.ComposeNewsTheme

@Composable
fun AppDrawer(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier = modifier) {
        CompNewsAppBranding(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp))
        NavigationDrawerItem(
            label = { Text(text = "Home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "" )},
            selected = currentRoute==HOME_ROUTE,
            onClick = {
                onNavigate(HOME_ROUTE)
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Interest") },
            icon = { Icon(Icons.Filled.FormatListNumberedRtl, contentDescription = "" )},
            selected = currentRoute==INTEREST_ROUTE,
            onClick = {
                onNavigate(INTEREST_ROUTE)
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
fun CompNewsAppBranding(modifier: Modifier) {
    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_jetnews_logo),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(18.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_jetnews_wordmark),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppDrawerPreview(modifier: Modifier = Modifier) {
    ComposeNewsTheme {
        AppDrawer(currentRoute = "", onNavigate = {}, closeDrawer = {})
    }
}