package com.example.composenews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenews.data.PostsRepository
import com.example.composenews.ui.homeScreen.HomeScreenRoute
import com.example.composenews.ui.homeScreen.HomeViewModel

const val POST_ID = "postId"
@Composable
fun ComposeNewsNavGraph(
    postsRepository: PostsRepository,
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    navController: NavHostController = rememberNavController(),
    openNavDrawer:()->Unit,
    startDestination:String=CompNewsDestinations.HOME_ROUTE,
) {

    NavHost(navController = navController, startDestination = startDestination,modifier = modifier){
        composable(
            route = CompNewsDestinations.HOME_ROUTE
        ){navBackStackEntry ->
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    postsRepository = postsRepository,
                    preSelectedPostId = navBackStackEntry.arguments?.getString(POST_ID)
                )
            )

            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreenRoute(
                uiState = uiState,
                isExpandedScreen = isExpandedScreen,
                onToggleFavorite = { homeViewModel.toggleFavourite(it) },
                onSelectPost = { homeViewModel.selectArticle(it) },
                onRefreshPosts = { homeViewModel.refreshPost() },
                onErrorDismiss = { homeViewModel.errorShown(it) },
                onInteractWithFeed = {},
                onInteractWithArticleDetails = {},
                onSearchInputChanged = {},
                openNavDrawer = openNavDrawer
            )
        }
        composable(
            route = CompNewsDestinations.INTEREST_ROUTE
        ){
            InterestScreen()
        }
    }

}

@Composable
fun InterestScreen() {
    Scaffold {
        val modifier = Modifier.padding(it)
        Column(modifier = modifier) {
            Text(text = "Text me plz : Interest ")
        }
    }
}