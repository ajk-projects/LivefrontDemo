package com.example.livefrontdemo.data.datastore.model

sealed class PublishPostResult {
    data object Success : PublishPostResult()
    data class Error(val message: String? = null) : PublishPostResult()
}