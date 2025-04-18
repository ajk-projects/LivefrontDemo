package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.datastore.FeedDataStore
import com.example.livefrontdemo.data.datastore.model.GetFeedResult
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.data.repository.model.TimelineResult
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataStore: FeedDataStore,
) : FeedRepository {
    private val timelineMutex = Mutex()

    private var timelineResult: TimelineResult = TimelineResult.None

    override suspend fun getMyTimeline(refresh: Boolean): TimelineResult {
        if (refresh || timelineResult is TimelineResult.None || timelineResult is TimelineResult.Error) {
            val feedResult = feedDataStore.getTimeline()
            timelineMutex.withLock {
                this.timelineResult = when (feedResult) {
                    is GetFeedResult.Success -> {
                        TimelineResult.Success(
                            posts = feedResult.feed?.map { feedItem ->
                                TimelinePost(
                                    text = feedItem.post?.record?.text.orEmpty(),
                                    avatarUrl = feedItem.post?.author?.avatar,
                                    authorName = feedItem.post?.author?.displayName,
                                    authorHandle = feedItem.post?.author?.handle,
                                    createdDate = feedItem.post?.record?.createdAt,
                                )
                            } ?: emptyList()
                        )
                    }

                    is GetFeedResult.Error -> TimelineResult.Error(message = feedResult.message)
                }
            }
        }
        return timelineMutex.withLock { this.timelineResult }
    }
}