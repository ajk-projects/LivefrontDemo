package com.example.livefrontdemo.view.stateholder.model

import androidx.annotation.StringRes

sealed class PostState {
    data object Unknown : PostState()
    data object Posting : PostState()
    data object Success : PostState()
    data class Error(@StringRes val message: Int) : PostState()
}