package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.datastore.FeedDataStore
import com.example.livefrontdemo.data.datastore.model.GetFeedResult
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.data.repository.model.exception.FeedException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataStore: FeedDataStore,
) : FeedRepository {
    private val timelineMutex = Mutex()

    private var timeline: List<TimelinePost> = emptyList()

    @Throws(FeedException::class)
    override suspend fun getMyTimeline(refresh: Boolean): List<TimelinePost> {
        if (refresh || timeline.isEmpty()) {
            val feedResult = feedDataStore.getTimeline()
            timelineMutex.withLock {
                this.timeline = when (feedResult) {
                    is GetFeedResult.Success -> feedResult.feed?.map { feedItem ->
                        TimelinePost(
                            text = feedItem.post?.record?.text.orEmpty(),
                            avatarUrl = feedItem.post?.author?.avatar,
                            authorName = feedItem.post?.author?.displayName,
                            authorHandle = feedItem.post?.author?.handle,
                            createdDate = feedItem.post?.record?.createdAt,
                        )
                    } ?: emptyList()

                    is GetFeedResult.Error -> throw FeedException()
                }
            }
        }
        return timelineMutex.withLock { this.timeline }
    }
}