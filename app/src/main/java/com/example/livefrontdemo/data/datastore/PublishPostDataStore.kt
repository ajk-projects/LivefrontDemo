package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.datastore.model.PublishPostResult

interface PublishPostDataStore {
    /**
     * Publish a post to the user's timeline.
     *
     * @param text The text of the post.
     * @return A result class encapsulating a success or error.
     */
    suspend fun publishPost(text: String): PublishPostResult
}