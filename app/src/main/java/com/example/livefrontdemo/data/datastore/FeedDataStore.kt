package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.datastore.model.GetFeedResult

interface FeedDataStore {
    /**
     * Get the the timeline for the current signed-in user (based on accessJwt header).
     *
     * @return A result class encapsulating a success (with timeline list), or error.
     */
    suspend fun getTimeline(): GetFeedResult
}