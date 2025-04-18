package com.example.livefrontdemo.view.compose.feed

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.reusable.AvatarImage
import com.example.livefrontdemo.view.stateholder.model.FeedState
import com.example.livefrontdemo.view.util.NoInputHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeedRootView(
    feedState: FeedState,
    fetchFeed: NoInputHandler,
) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<TimelinePost>()
    val scope = rememberCoroutineScope()

    fun navigateToItem(item: TimelinePost): NoInputHandler = {
        scope.launch {
            scaffoldNavigator.navigateTo(
                ListDetailPaneScaffoldRole.Detail,
                item
            )
        }
    }

    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                PullToRefreshBox(
                    isRefreshing = feedState is FeedState.Loading,
                    onRefresh = fetchFeed,
                ) {
                    Crossfade(
                        targetState = feedState,
                    ) { state ->

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item { Spacer(modifier = Modifier.height(8.dp)) }
                            when (state) {
                                is FeedState.Success -> {
                                    items(items = state.posts) { item ->
                                        FeedPostView(
                                            post = item,
                                            postTapped = navigateToItem(item),
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }

                                is FeedState.Error -> item {
                                    Text(
                                        text = stringResource(id = state.message),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp),
                                    )
                                }

                                else -> Unit
                            }
                            item { Spacer(modifier = Modifier.height(8.dp)) }
                        }
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                scaffoldNavigator.currentDestination?.contentKey?.let { post ->
                    FeedDetailView(
                        post = post,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                    )
                }
            }
        },
        extraPane = {
            AnimatedPane(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                scaffoldNavigator.currentDestination?.contentKey?.avatarUrl?.let { postImageUrl ->
                    AvatarImage(
                        imageUrl = postImageUrl,
                        contentDescription = "enlarged avatar image",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun FeedRootViewPreview() {
    val timelinePost = TimelinePost(
        authorName = "John Doe",
        authorHandle = "johndoe.com",
        avatarUrl = "",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
    )
    LivefrontDemoTheme {
        FeedRootView(
            fetchFeed = {},
            feedState = FeedState.Success(
                posts = listOf(
                    timelinePost,
                    timelinePost,
                    timelinePost,
                    timelinePost,
                    timelinePost,
                    timelinePost,
                    timelinePost,
                ),
            )
        )
    }
}

@PreviewFontScale
@Composable
private fun FeedRootViewPreviewFonts() {
    val timelinePost = TimelinePost(
        authorName = "John Doe",
        authorHandle = "johndoe.com",
        avatarUrl = "",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
    )
    LivefrontDemoTheme {
        FeedRootView(
            fetchFeed = {},
            feedState = FeedState.Success(posts = listOf(timelinePost))
        )
    }
}