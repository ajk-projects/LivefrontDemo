package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.api.BlueSkyApi
import com.example.livefrontdemo.data.datastore.model.GetAuthorResult
import javax.inject.Inject

class AuthorDataStoreImpl @Inject constructor(
    private val blueSkyApi: BlueSkyApi,
) : AuthorDataStore {
    override suspend fun getAuthorDetails(dIdOrHandle: String): GetAuthorResult = runCatching {
        blueSkyApi.getProfile(actor = dIdOrHandle)
    }.mapCatching { response ->
        val profileResponse = response.body()
        if (response.isSuccessful && profileResponse != null) {
            GetAuthorResult.Success(author = profileResponse)
        } else {
            val errorBody = response.errorBody()
            GetAuthorResult.Error(message = errorBody.toString())
        }
    }.onFailure { throwable ->
        GetAuthorResult.Error()
    }.getOrElse {
        GetAuthorResult.Error()
    }
}