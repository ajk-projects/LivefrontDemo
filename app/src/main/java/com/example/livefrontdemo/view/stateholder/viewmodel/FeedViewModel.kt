package com.example.livefrontdemo.view.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.data.repository.model.TimelineResult
import com.example.livefrontdemo.view.stateholder.model.FeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
) : ViewModel() {
    private val _feedState = MutableStateFlow<FeedState>(FeedState.Unknown)
    val feedState: StateFlow<FeedState> = _feedState.asStateFlow()

    init {
        getFeed()
    }

    fun getFeed(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (forceRefresh) _feedState.value = FeedState.Loading
        _feedState.value = when (val result = feedRepository.getMyTimeline(refresh = forceRefresh)) {
            is TimelineResult.Success -> FeedState.Success(posts = result.posts)
            is TimelineResult.Error -> FeedState.Error(message = R.string.no_posts_found_error)
            is TimelineResult.None -> FeedState.Unknown
        }
    }
}