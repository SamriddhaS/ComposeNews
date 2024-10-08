package com.example.composenews.ui.homeScreen.homeFeedUi

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
import com.example.composenews.ui.homeScreen.HomeUiStates
import com.example.composenews.ui.homeScreen.SnackBarHost
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeFeedScreen(
    uiState: HomeUiStates,
    showTopAppBar: Boolean,
    onToggleFavorite: (String) -> Unit,
    onSelectPost: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (String) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    searchInput: String = "",
    onSearchInputChanged: (String) -> Unit,
) {
    HomeScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onRefreshPost = onRefreshPosts,
        onErrorDismiss = onErrorDismiss,
        openNavDrawer = openDrawer,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    ) { uiStateHasPost,contentPadding, modifier ->

        HomePostList(
            uiStateHasPost.postsFeed,
            uiStateHasPost.favorites,
            !showTopAppBar,
            onToggleFavorite,
            onSelectPost,
            contentPadding,
            homeListLazyListState,
            searchInput,
            onSearchInputChanged,
            modifier
        )
    }
}

@Composable
fun HomePostList(
    postsFeed: PostsFeed,
    favorites: Set<String>,
    showTopAppBar: Boolean,
    onToggleFavorite: (String) -> Unit,
    onSelectPost: (postId: String) -> Unit,
    contentPadding: PaddingValues,
    homeListLazyListState: LazyListState = rememberLazyListState(),
    searchInput: String,
    onSearchInputChanged: (String) -> Unit,
    modifier: Modifier
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = homeListLazyListState
    ) {

        if (showTopAppBar){
            /**
             * TODO : Added search bar for expanded screens
             * */
            /**
             * TODO : Added search bar for expanded screens
             * */
            item {

            }
        }

        /**
         * Highlighted Stories Section
         * */

        /**
         * Highlighted Stories Section
         * */
        item {
            PostListHighlightedStoriesSection(postsFeed.highlightedPost,onSelectPost)
        }

        /**
         * Recommended Stories Section
         * */

        /**
         * Recommended Stories Section
         * */
        if(postsFeed.recommendedPosts.isNotEmpty()){
            item{
                PostListRecommendedSection(
                    recommendedPostsList = postsFeed.recommendedPosts,
                    onSelectPost,
                    favorites,
                    onToggleFavorite
                )
            }
        }

        /**
         * Popular posts Section
         * */

        /**
         * Popular posts Section
         * */
        if(!postsFeed.popularPosts.isNullOrEmpty()){
            item {
                PostListPopularSection(
                    postsFeed.popularPosts,
                    onSelectPost
                )
            }
        }

        /**
         * Recent Posts
         * */

        /**
         * Recent Posts
         * */
        if(!postsFeed.recentPosts.isNullOrEmpty()){
            item {
                PostsListRecentPostUi(
                    postsFeed.recentPosts,
                    onSelectPost
                )
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithList(
    uiState: HomeUiStates,
    showTopAppBar:Boolean,
    onRefreshPost:()->Unit,
    onErrorDismiss:(String)->Unit,
    openNavDrawer:()->Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    hasPostContent:@Composable (
        uiState: HomeUiStates.HasPosts,
        contentPadding: PaddingValues,
        modifier: Modifier
    ) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    Scaffold(
        topBar = {
            if(showTopAppBar){
                TopAppBarState(
                    openNavDrawer = openNavDrawer,
                    topAppBarState = topAppBarState
                )
            }
        },
        snackbarHost = { SnackBarHost(hostState = snackbarHostState) },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        LoadingContent(
            empty = when(uiState){
                is HomeUiStates.HasPosts -> false
                is HomeUiStates.NoPosts -> uiState.isLoading
            } ,
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading ,
            onRefresh = onRefreshPost
        ) {
            when(uiState){
                is HomeUiStates.NoPosts -> {
                    if (uiState.errorMessages.isEmpty()){
                        // if there are no posts, and no error, let the user refresh manually
                        TextButton(
                            onClick = onRefreshPost,
                            modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            Text(
                                "Tap to load content",
                                textAlign = TextAlign.Center
                            )
                        }

                    }else{
                        // there's currently an error showing, no need to show any content
                        Box(
                            contentModifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        )
                    }
                }
                is HomeUiStates.HasPosts -> {
                    hasPostContent(uiState,innerPadding,contentModifier)
                }
            }
        }

    }

    if (uiState.errorMessages.isNotEmpty()){
        val errorMessageText = remember(uiState) { uiState.errorMessages[0].messageId }
        val retryText = "Retry"

        // If onRefreshPosts or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        val onRefreshPostsState by rememberUpdatedState(onRefreshPost)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(errorMessageText, retryText, snackbarHostState) {
            val snackResult = snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryText
            )

            if (snackResult== SnackbarResult.ActionPerformed){
                onRefreshPostsState()
            }

            onErrorDismissState(errorMessageText)
        }

    }


}

/**
 * LoadingContent composable handel's our swipe to refresh layout
 * along with the empty content management.
 * Display an initial empty state or swipe to refresh content.
 *  @param empty (state) when true, display [emptyContent]
 *  @param emptyContent (slot) the content to display for the empty state
 *  @param loading (state) when true, display a loading spinner over [content]
 *  @param onRefresh (event) event to request refresh
 *  @param content (slot) the main content to show
 * */
@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

/**
 * Full screen circular progress indicator
 */
@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
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