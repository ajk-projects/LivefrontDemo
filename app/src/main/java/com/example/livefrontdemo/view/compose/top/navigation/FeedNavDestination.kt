package com.example.livefrontdemo.view.compose.top.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.livefrontdemo.view.compose.feed.FeedRootView
import com.example.livefrontdemo.view.compose.model.TopNavDestination
import com.example.livefrontdemo.view.stateholder.viewmodel.FeedViewModel

fun NavGraphBuilder.feedNavDestination() {
    composable(TopNavDestination.Feed.route) {
        val feedViewModel: FeedViewModel = hiltViewModel()
        val feedState by feedViewModel.feedState.collectAsStateWithLifecycle()

        FeedRootView(
            fetchFeed = {
                feedViewModel.getFeed(forceRefresh = true)
            },
            feedState = feedState,
        )
    }
}