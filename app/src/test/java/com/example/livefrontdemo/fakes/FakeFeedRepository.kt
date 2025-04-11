package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.getTimelinePosts
import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.data.repository.model.exception.FeedException

class FakeFeedRepository(private val throwError: Boolean = false) : FeedRepository {
    override suspend fun getMyTimeline(refresh: Boolean): List<TimelinePost> {
        if (throwError) throw FeedException()
        return if (refresh) {
            getTimelinePosts(count = 2)
        } else {
            getTimelinePosts()
        }
    }
}