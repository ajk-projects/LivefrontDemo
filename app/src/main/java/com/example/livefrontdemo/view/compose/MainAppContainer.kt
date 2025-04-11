package com.example.livefrontdemo.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.livefrontdemo.view.compose.model.ActivityActionListener
import com.example.livefrontdemo.view.compose.top.AppRootView
import com.example.livefrontdemo.view.compose.top.FullScreenLoadingView
import com.example.livefrontdemo.view.compose.top.LoginView
import com.example.livefrontdemo.view.stateholder.model.AuthState
import com.example.livefrontdemo.view.stateholder.viewmodel.AuthViewModel

@Composable
fun MainAppContainer() {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    when (authState) {
        is AuthState.Authenticated -> {
            AppRootView(
                activityActionListener = ActivityActionListener(
                    signOut = authViewModel::signOut
                )
            )
        }

        is AuthState.Unknown,
        is AuthState.UnAuthenticated,
            -> {
            val error = (authState as? AuthState.UnAuthenticated)?.withError
            LoginView(
                initSession = authViewModel::signIn,
                errorMessage = error,
            )
        }

        is AuthState.ProcessingAuthChange -> {
            FullScreenLoadingView()
        }
    }
}