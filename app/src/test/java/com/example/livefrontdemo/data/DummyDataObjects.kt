package com.example.livefrontdemo.data

import com.example.livefrontdemo.data.api.model.AuthorApiModel
import com.example.livefrontdemo.data.api.model.FeedItemApiModel
import com.example.livefrontdemo.data.api.model.PostRecordApiModel
import com.example.livefrontdemo.data.api.model.PostViewApiModel
import com.example.livefrontdemo.data.repository.model.TimelinePost
import java.util.Date

internal val timelinePost = TimelinePost(
    text = "",
    avatarUrl = "",
    authorName = "AJ Kueterman",
    authorHandle = "ajkueterman.com",
    createdDate = Date(),
)

internal fun getTimelinePosts(count: Int = 1): List<TimelinePost> {
    val posts = mutableListOf<TimelinePost>()
    repeat(count) {
        posts.add(timelinePost)
    }
    return posts
}

internal val feedPost = FeedItemApiModel(
    post = PostViewApiModel(
        author = AuthorApiModel(
            displayName = "AJ Kueterman",
            handle = "ajkueterman.com",
        ),
        record = PostRecordApiModel(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
        ),
    )
)

internal val feedPosts = listOf(
    feedPost,
    feedPost,
    feedPost,
    feedPost,
    feedPost,
)