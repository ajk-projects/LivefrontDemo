package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.datastore.PublishPostDataStore
import com.example.livefrontdemo.data.datastore.model.PublishPostResult

class FakePublishPostDataStore(val returnError: Boolean = false) : PublishPostDataStore {
    override suspend fun publishPost(text: String): PublishPostResult {
        return if (returnError) {
            PublishPostResult.Error()
        } else {
            PublishPostResult.Success
        }
    }
}