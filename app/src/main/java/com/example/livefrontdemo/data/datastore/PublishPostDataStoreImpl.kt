package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.api.BlueSkyApi
import com.example.livefrontdemo.data.api.model.PostRecordApiModel
import com.example.livefrontdemo.data.api.model.PostRequestApiModel
import com.example.livefrontdemo.data.datastore.model.PublishPostResult
import com.example.livefrontdemo.data.repository.AuthRepository
import java.util.Date
import javax.inject.Inject

class PublishPostDataStoreImpl @Inject constructor(
    private val blueSkyApi: BlueSkyApi,
    private val authRepository: AuthRepository,
) : PublishPostDataStore {
    override suspend fun publishPost(text: String): PublishPostResult = runCatching {
        blueSkyApi.publishPost(
            body = PostRequestApiModel(
                record = PostRecordApiModel(
                    text = text,
                    createdAt = Date(),
                ),
                repo = authRepository.getDid(),
            )
        )
    }.mapCatching { response ->
        val postResponse = response.body()
        if (response.isSuccessful && postResponse != null) {
            PublishPostResult.Success
        } else {
            val errorBody = response.errorBody()
            PublishPostResult.Error(message = errorBody.toString())
        }
    }.onFailure { throwable ->
        PublishPostResult.Error()
    }.getOrElse {
        PublishPostResult.Error()
    }
}