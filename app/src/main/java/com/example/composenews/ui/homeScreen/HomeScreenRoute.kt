package com.example.composenews.ui.homeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.composenews.R
import com.example.composenews.models.PostsFeed
import com.example.composenews.ui.homeScreen.homeFeedUi.HomeFeedScreen
import com.example.composenews.ui.homeScreen.homeFeedUi.PostListHighlightedStoriesSection
import com.example.composenews.ui.homeScreen.homeFeedUi.PostListPopularSection
import com.example.composenews.ui.homeScreen.homeFeedUi.PostListRecommendedSection
import com.example.composenews.ui.homeScreen.homeFeedUi.PostsListRecentPostUi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

enum class HomeScreenType {
    FeedWithArticleDetails, // For Tablets and folding phones.
    JustFeed, // For Normal devices
    ArticleDetails // Details screen
}

/**
 * Returns the current [HomeScreenType] to display, based on whether or not the screen is expanded
 * and the [HomeUiState].
 */
@Composable
private fun getHomeScreenType(
    isExpandedScreen: Boolean,
    uiState: HomeUiStates
): HomeScreenType = when (isExpandedScreen) {
    false -> {
        when (uiState) {
            is HomeUiStates.HasPosts -> {
                if (uiState.isArticleOpen) {
                    HomeScreenType.ArticleDetails
                } else {
                    HomeScreenType.JustFeed
                }
            }
            is HomeUiStates.NoPosts -> HomeScreenType.JustFeed
        }
    }
    true -> HomeScreenType.FeedWithArticleDetails
}


@Composable
fun HomeScreenRoute(
    uiState: HomeUiStates,
    isExpandedScreen: Boolean,
    onToggleFavorite: (String) -> Unit,
    onSelectPost: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (String) -> Unit,
    onInteractWithFeed: () -> Unit,
    onInteractWithArticleDetails: (String) -> Unit,
    onSearchInputChanged: (String) -> Unit,
    openNavDrawer:()->Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {

    val homeListLazyListState = rememberLazyListState()
    val loadHomeScreenType = getHomeScreenType(
        isExpandedScreen = isExpandedScreen,
        uiState = uiState
    )

    when(loadHomeScreenType){
        HomeScreenType.JustFeed -> {
            HomeFeedScreen(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onToggleFavorite = onToggleFavorite,
                onSelectPost = onSelectPost,
                onRefreshPosts = onRefreshPosts,
                onErrorDismiss = onErrorDismiss,
                openDrawer = openNavDrawer,
                homeListLazyListState = homeListLazyListState,
                snackbarHostState = snackbarHostState,
                onSearchInputChanged = onSearchInputChanged
            )
        }
        HomeScreenType.ArticleDetails -> {

        }
        HomeScreenType.FeedWithArticleDetails -> {

        }
    }
}
