package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.api.model.ProfileResponseApiModel
import com.example.livefrontdemo.data.datastore.AuthorDataStore
import com.example.livefrontdemo.data.datastore.model.GetAuthorResult

class FakeAuthorDataStore(val returnError: Boolean = false) : AuthorDataStore {
    override suspend fun getAuthorDetails(dIdOrHandle: String): GetAuthorResult {
        return if (returnError) {
            GetAuthorResult.Error()
        } else {
            GetAuthorResult.Success(
                author = ProfileResponseApiModel(
                    handle = dIdOrHandle,
                    did = dIdOrHandle,
                )
            )
        }
    }
}