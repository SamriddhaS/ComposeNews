package com.example.composenews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ComposeNewsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openNavDrawer:()->Unit,
    startDestination:String=CompNewsDestinations.HOME_ROUTE,
) {

    NavHost(navController = navController, startDestination = startDestination,modifier = modifier){
        composable(
            route = CompNewsDestinations.HOME_ROUTE
        ){
            HomeScreen()
        }
        composable(
            route = CompNewsDestinations.INTEREST_ROUTE
        ){
            InterestScreen()
        }
    }

}

@Composable
fun HomeScreen() {
    Scaffold {
        val modifier = Modifier.padding(it)
        Column(modifier = modifier) {
            Text(text = "Text me plz : Home ")
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