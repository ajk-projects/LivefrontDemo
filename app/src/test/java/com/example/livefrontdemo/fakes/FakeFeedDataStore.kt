package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.datastore.FeedDataStore
import com.example.livefrontdemo.data.datastore.model.GetFeedResult
import com.example.livefrontdemo.data.feedPosts

class FakeFeedDataStore(val returnError: Boolean = false) : FeedDataStore {
    override suspend fun getTimeline(): GetFeedResult = if (returnError) {
        GetFeedResult.Error()
    } else {
        GetFeedResult.Success(feed = feedPosts)
    }
}