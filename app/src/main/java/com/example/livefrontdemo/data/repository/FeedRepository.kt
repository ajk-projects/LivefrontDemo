package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.repository.model.TimelineResult

interface FeedRepository {
    /**
     * Get the user's timeline / feed. Pulls from the network or in-memory cache.
     *
     * @param refresh Pull from the network & refresh the cache. Defaults to false.
     * @return A [TimelineResult] containing the user's timeline list or error.
     */
    suspend fun getMyTimeline(refresh: Boolean = false): TimelineResult
}