package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.api.BlueSkyApi
import com.example.livefrontdemo.data.datastore.model.GetFeedResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedDataStoreImpl @Inject constructor(
    private val blueSkyApi: BlueSkyApi,
    private val ioDispatcher: CoroutineDispatcher,
) : FeedDataStore {
    override suspend fun getTimeline(): GetFeedResult = withContext(ioDispatcher) {
        runCatching {
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
}