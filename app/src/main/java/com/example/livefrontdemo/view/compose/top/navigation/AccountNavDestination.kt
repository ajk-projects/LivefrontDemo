package com.example.livefrontdemo.view.compose.top.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.livefrontdemo.view.compose.account.AccountRootView
import com.example.livefrontdemo.view.compose.model.TopNavDestination
import com.example.livefrontdemo.view.stateholder.viewmodel.AccountViewModel

fun NavGraphBuilder.accountNavDestination() {
    composable(TopNavDestination.Account.route) {
        val accountViewModel: AccountViewModel = hiltViewModel()
        val accountState by accountViewModel.accountState.collectAsStateWithLifecycle()

        AccountRootView(
            accountState = accountState,
            modifier = Modifier.fillMaxSize()
        )
    }
}