package com.example.livefrontdemo.wiring

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Default
    @Provides
    fun provideDefaultSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE)

    @Encrypted
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_PREFS_FILE_NAME,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private const val ENCRYPTED_PREFS_FILE_NAME = "encrypted_shared_prefs"
    private const val PREFS_FILE_NAME = "default_shared_prefs"
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Default

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Encrypted