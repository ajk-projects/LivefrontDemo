package com.example.livefrontdemo.wiring

import com.example.livefrontdemo.data.datastore.AuthDataStore
import com.example.livefrontdemo.data.datastore.AuthDataStoreImpl
import com.example.livefrontdemo.data.datastore.AuthorDataStore
import com.example.livefrontdemo.data.datastore.AuthorDataStoreImpl
import com.example.livefrontdemo.data.datastore.FeedDataStore
import com.example.livefrontdemo.data.datastore.FeedDataStoreImpl
import com.example.livefrontdemo.data.datastore.PublishPostDataStore
import com.example.livefrontdemo.data.datastore.PublishPostDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {
    @Binds
    fun bindAuthDataStore(authDataStoreImpl: AuthDataStoreImpl): AuthDataStore

    @Binds
    fun bindFeedDataStore(feedDataStoreImpl: FeedDataStoreImpl): FeedDataStore

    @Binds
    fun bindAuthorDataStore(authorDataStoreImpl: AuthorDataStoreImpl): AuthorDataStore

    @Binds
    fun bindPublishPostDataStore(publishPostDataStoreImpl: PublishPostDataStoreImpl): PublishPostDataStore
}