package com.example.livefrontdemo.view.compose.model

import androidx.annotation.StringRes
import com.example.livefrontdemo.R

sealed class TopNavDestination(
    val route: String,
    @StringRes val title: Int,
) {
    object Feed : TopNavDestination(route = "feed", title = R.string.feed)
    object Account : TopNavDestination(route = "account", title = R.string.account)

    companion object {
        fun titleForRoute(route: String?): Int {
            return when (route) {
                Feed.route -> Feed.title
                Account.route -> Account.title
                else -> R.string.app_name
            }
        }
    }
}