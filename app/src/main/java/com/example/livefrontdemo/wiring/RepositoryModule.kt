package com.example.livefrontdemo.wiring

import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.AccountRepositoryImpl
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.data.repository.AuthRepositoryImpl
import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.data.repository.FeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindFeedRepository(feedRepositoryImpl: FeedRepositoryImpl): FeedRepository

    @Singleton
    @Binds
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository
}