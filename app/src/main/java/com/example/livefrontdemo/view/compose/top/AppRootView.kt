package com.example.livefrontdemo.view.compose.top

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.livefrontdemo.R
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.model.ActivityActionListener
import com.example.livefrontdemo.view.compose.model.TopNavDestination
import com.example.livefrontdemo.view.compose.posting.ComposePostBottomSheetView
import com.example.livefrontdemo.view.compose.top.navigation.AppNavHost
import com.example.livefrontdemo.view.stateholder.viewmodel.PublishPostViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRootView(
    activityActionListener: ActivityActionListener,
) {
    val postViewModel: PublishPostViewModel = hiltViewModel()
    val postState by postViewModel.postState.collectAsStateWithLifecycle()
    val showBottomSheet by postViewModel.displayComposeBottomSheet.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 2.dp,
            ) {
                TopAppBar(
                    title = {
                        AnimatedContent(currentRoute) { route ->
                            Text(
                                text = stringResource(id = TopNavDestination.titleForRoute(route)),
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                            )
                        }
                    },
                    actions = {
                        AnimatedVisibility(
                            visible = currentRoute == TopNavDestination.Account.route,
                            enter = fadeIn(),
                            exit = fadeOut(),
                        ) {
                            TextButton(
                                onClick = activityActionListener.signOut,
                            ) {
                                Text(text = stringResource(R.string.sign_out))
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = currentRoute == TopNavDestination.Feed.route,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                FloatingActionButton(
                    onClick = postViewModel::displayComposeView,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Create,
                        contentDescription = stringResource(id = R.string.compose_new_post_desc),
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = currentRoute == TopNavDestination.Feed.route,
                    onClick = {
                        navController.navigate(TopNavDestination.Feed.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.List,
                            contentDescription = stringResource(id = R.string.feed_bottom_bar_item),
                        )
                    },
                )
                NavigationBarItem(
                    selected = currentRoute == TopNavDestination.Account.route,
                    onClick = {
                        navController.navigate(TopNavDestination.Account.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = stringResource(id = R.string.account_bottom_bar_item),
                        )
                    },
                )
            }
        },
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = postViewModel::dismissComposeView,
                sheetState = sheetState,
                dragHandle = null,
            ) {
                ComposePostBottomSheetView(
                    postState = postState,
                    dismissBottomSheet = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                postViewModel.dismissComposeView()
                            }
                        }
                    },
                    submitPost = postViewModel::publishPost,
                )
            }
        }
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun AppRootViewPreview() {
    LivefrontDemoTheme {
        AppRootView(
            activityActionListener = ActivityActionListener(),
        )
    }
}