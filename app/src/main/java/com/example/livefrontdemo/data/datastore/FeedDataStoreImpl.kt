package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.api.BlueSkyApi
import com.example.livefrontdemo.data.datastore.model.GetFeedResult
import javax.inject.Inject

class FeedDataStoreImpl @Inject constructor(
    private val blueSkyApi: BlueSkyApi,
) : FeedDataStore {
    override suspend fun getTimeline(): GetFeedResult = runCatching {
        blueSkyApi.getTimeline()
    }.mapCatching { response ->
        val feedResponse = response.body()
        if (response.isSuccessful && feedResponse != null) {
            GetFeedResult.Success(feed = feedResponse.feed)
        } else {
            val errorBody = response.errorBody()
            GetFeedResult.Error(message = errorBody.toString())
        }
    }.onFailure { throwable ->
        GetFeedResult.Error()
    }.getOrElse {
        GetFeedResult.Error()
    }
}