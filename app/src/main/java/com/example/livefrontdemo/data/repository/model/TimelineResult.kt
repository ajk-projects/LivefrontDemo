package com.example.livefrontdemo.data.repository.model

sealed class TimelineResult {
    data object None : TimelineResult()

    data class Success(
        val posts: List<TimelinePost>,
    ) : TimelineResult()

    data class Error(
        val message: String? = null,
    ) : TimelineResult()
}