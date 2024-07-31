package com.example.composenews.ui.homeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.composenews.R

@Composable
fun HomeScreenRoute(
    openNavDrawer:()->Unit,
    isExpandedScreen: Boolean,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    HomeFeedScreen(
        openNavDrawer = openNavDrawer,
        isExpandedScreen = isExpandedScreen,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun HomeFeedScreen(
    openNavDrawer:()->Unit,
    isExpandedScreen: Boolean,
    snackbarHostState: SnackbarHostState
) {
    HomeScreenWithList(
        showTopAppBar = true,
        onRefreshPost = { /*TODO*/ },
        onErrorDismiss = {},
        openNavDrawer = openNavDrawer,
        snackbarHostState = snackbarHostState
    ) {
        contentPadding, modifier ->

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithList(
    showTopAppBar:Boolean,
    onRefreshPost:()->Unit,
    onErrorDismiss:(Long)->Unit,
    openNavDrawer:()->Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    hasPostContent:@Composable (
        contentPadding: PaddingValues,
        modifier: Modifier
    ) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    Scaffold(
        topBar = {
            if(showTopAppBar){
                TopAppBarState(
                    openNavDrawer = openNavDrawer,
                    topAppBarState = topAppBarState
                )
            }
        },
        snackbarHost = {
        },
        modifier = modifier
    ) {
        val it = Modifier.padding(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarState(
    openNavDrawer: () -> Unit,
    topAppBarState: TopAppBarState,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(R.drawable.ic_jetnews_wordmark),
                contentDescription = "title",
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.fillMaxWidth()
            ) },
        navigationIcon = {
            IconButton(onClick = openNavDrawer) {
                Icon(
                    painter = painterResource(R.drawable.ic_jetnews_logo),
                    contentDescription = "Drawer Button",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "Search is not yet implemented in this configuration",
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search article"
                )
            }

        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}