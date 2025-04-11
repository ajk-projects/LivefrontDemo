package com.example.livefrontdemo.data.api.model

import com.squareup.moshi.JsonClass
import java.util.Date

data class FeedResponseApiModel(
    val feed: List<FeedItemApiModel>? = null,
    val cursor: String? = null,
)

@JsonClass(generateAdapter = true)
data class FeedItemApiModel(
    val post: PostViewApiModel? = null,
    val reply: ReplyRefApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class ReplyRefApiModel(
    val root: PostViewApiModel? = null,
    val parent: PostViewApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class PostViewApiModel(
    val uri: String? = null,
    val cid: String? = null,
    val author: AuthorApiModel? = null,
    val record: PostRecordApiModel? = null,
    val replyCount: Int? = null,
    val repostCount: Int? = null,
    val likeCount: Int? = null,
    val quoteCount: Int? = null,
    val indexedAt: String? = null,
    val viewer: ViewerStateApiModel? = null,
    val labels: List<Any>? = null,
)

@JsonClass(generateAdapter = true)
data class AuthorApiModel(
    val did: String? = null,
    val handle: String? = null,
    val displayName: String? = null,
    val avatar: String? = null,
    val viewer: AuthorViewerApiModel? = null,
    val labels: List<Any>? = null,
    val createdAt: String? = null,
)

@JsonClass(generateAdapter = true)
data class AuthorViewerApiModel(
    val muted: Boolean? = null,
    val blockedBy: Boolean? = null,
    val following: String? = null,
)

@JsonClass(generateAdapter = true)
data class PostRecordApiModel(
    val createdAt: Date? = null,
    val text: String? = null,
    val facets: List<FacetApiModel>? = null,
    val langs: List<String>? = null,
    val reply: ReplyRecordApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class FacetApiModel(
    val index: TextRangeApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class TextRangeApiModel(
    val byteEnd: Int? = null,
    val byteStart: Int? = null,
)

@JsonClass(generateAdapter = true)
data class ReplyRecordApiModel(
    val parent: RecordRefApiModel? = null,
    val root: RecordRefApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class RecordRefApiModel(
    val cid: String? = null,
    val uri: String? = null,
)

@JsonClass(generateAdapter = true)
data class ViewerStateApiModel(
    val threadMuted: Boolean? = null,
    val embeddingDisabled: Boolean? = null,
)

@JsonClass(generateAdapter = true)
data class ServiceApiModel(
    val id: String? = null,
    val type: String? = null,
    val serviceEndpoint: String? = null,
)

@JsonClass(generateAdapter = true)
data class PostResponseApiModel(
    val uri: String? = null,
    val cid: String? = null,
)

@JsonClass(generateAdapter = true)
data class PostRequestApiModel(
    val repo: String? = null,
    val collection: String = DEFAULT_FEED_COLLECTION,
    val record: PostRecordApiModel? = null,
) {
    private companion object {
        private const val DEFAULT_FEED_COLLECTION = "app.bsky.feed.post"
    }
}