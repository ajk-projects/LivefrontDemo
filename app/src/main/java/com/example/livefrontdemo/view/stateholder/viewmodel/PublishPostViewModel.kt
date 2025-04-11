package com.example.livefrontdemo.view.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.datastore.PublishPostDataStore
import com.example.livefrontdemo.data.datastore.model.PublishPostResult
import com.example.livefrontdemo.view.stateholder.model.PostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishPostViewModel @Inject constructor(
    private val publishPostDataStore: PublishPostDataStore,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _displayComposeBottomSheet = MutableStateFlow(false)
    val displayComposeBottomSheet = _displayComposeBottomSheet.asStateFlow()

    private val _postState = MutableStateFlow<PostState>(PostState.Unknown)
    val postState = _postState.asStateFlow()

    fun publishPost(text: String) {
        viewModelScope.launch(ioDispatcher) {
            _postState.value = PostState.Posting
            _postState.value = when (publishPostDataStore.publishPost(text)) {
                is PublishPostResult.Success -> {
                    dismissComposeView()
                    PostState.Success
                }

                is PublishPostResult.Error -> PostState.Error(message = R.string.posting_error)
            }
        }
    }

    fun displayComposeView() {
        _displayComposeBottomSheet.value = true
    }

    fun dismissComposeView() {
        _displayComposeBottomSheet.value = false
    }
}