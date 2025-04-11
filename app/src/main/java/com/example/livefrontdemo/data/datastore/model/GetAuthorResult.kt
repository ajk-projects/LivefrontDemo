package com.example.livefrontdemo.data.datastore.model

import com.example.livefrontdemo.data.api.model.ProfileResponseApiModel

sealed class GetAuthorResult {
    data class Success(val author: ProfileResponseApiModel) : GetAuthorResult()
    data class Error(val message: String? = null) : GetAuthorResult()
}