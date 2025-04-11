package com.example.livefrontdemo.data.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class TimelinePost(
    val text: String,
    val avatarUrl: String? = null,
    val authorName: String? = null,
    val authorHandle: String? = null,
    val createdDate: Date? = null,
) : Parcelable