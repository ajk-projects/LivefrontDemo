package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.data.repository.model.exception.FeedException

interface FeedRepository {
    /**
     * Get the user's timeline / feed. Pulls from the network or in-memory cache.
     *
     * @param refresh Pull from the network & refresh the cache. Defaults to false.
     * @return A list representing the users timeline (top 50 posts).
     * @throws FeedException If the timeline cannot be retrieved.
     */
    @Throws(FeedException::class)
    suspend fun getMyTimeline(refresh: Boolean = false): List<TimelinePost>
}