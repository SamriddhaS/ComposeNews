package com.example.composenews.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composenews.data.PostsRepository
import com.example.composenews.ui.CompNewsDestinations.HOME_ROUTE
import com.example.composenews.ui.theme.ComposeNewsTheme
import kotlinx.coroutines.launch

@Composable
fun CompNewsApp(
    postsRepository: PostsRepository,
    widthSizeClass: WindowWidthSizeClass
) {
    ComposeNewsTheme {

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: HOME_ROUTE
        val coroutineScope = rememberCoroutineScope()
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        val drawerState = rememberSizeAwareDrawerState(isExpandedScreen)

        ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    onNavigate = { destination -> navController.navigate(destination) },
                    closeDrawer = { coroutineScope.launch { drawerState.close() }  })
        }) {
            //Main content of the app
            ComposeNewsNavGraph(
                postsRepository = postsRepository,
                navController = navController,
                isExpandedScreen = isExpandedScreen,
                openNavDrawer = { coroutineScope.launch { drawerState.open() } },
                startDestination = HOME_ROUTE
                )

        }
    }
}

@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}
