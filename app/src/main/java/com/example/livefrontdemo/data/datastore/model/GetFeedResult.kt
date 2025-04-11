package com.example.livefrontdemo.data.datastore.model

import com.example.livefrontdemo.data.api.model.FeedItemApiModel

sealed class GetFeedResult {
    data class Success(
        val feed: List<FeedItemApiModel>? = null,
    ) : GetFeedResult()

    data class Error(
        val message: String? = null,
    ) : GetFeedResult()
}