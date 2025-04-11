package com.example.livefrontdemo.view.compose.top.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.livefrontdemo.view.compose.model.TopNavDestination

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TopNavDestination.Feed.route,
        modifier = modifier,
    ) {
        feedNavDestination()
        accountNavDestination()
    }
}