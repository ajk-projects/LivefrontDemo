package com.example.livefrontdemo.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponseApiModel(
    val did: String? = null,
    val handle: String? = null,
    val displayName: String? = null,
    val description: String? = null,
    val avatar: String? = null,
    val banner: String? = null,
    val followersCount: Int? = null,
    val followsCount: Int? = null,
    val postsCount: Int? = null,
    val associated: AssociatedDataApiModel? = null,
    val joinedViaStarterPack: StarterPackApiModel? = null,
    val indexedAt: String? = null,
    val createdAt: String? = null,
    val viewer: ProfileViewerApiModel? = null,
    val labels: List<LabelInfoApiModel>? = null,
    val pinnedPost: PinnedPostApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class AssociatedDataApiModel(
    val lists: Int? = null,
    val feedgens: Int? = null,
    val starterPacks: Int? = null,
    val labeler: Boolean? = null,
    val chat: ChatSettingsApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class ChatSettingsApiModel(
    val allowIncoming: String? = null,
)

@JsonClass(generateAdapter = true)
data class StarterPackApiModel(
    val uri: String? = null,
    val cid: String? = null,
    val record: Map<String, Any>? = null,
    val creator: ProfileCreatorApiModel? = null,
    val listItemCount: Int? = null,
    val joinedWeekCount: Int? = null,
    val joinedAllTimeCount: Int? = null,
    val labels: List<LabelInfoApiModel>? = null,
    val indexedAt: String? = null,
)

@JsonClass(generateAdapter = true)
data class ProfileCreatorApiModel(
    val did: String? = null,
    val handle: String? = null,
    val displayName: String? = null,
    val avatar: String? = null,
    val associated: AssociatedDataApiModel? = null,
    val viewer: CreatorViewerApiModel? = null,
    val labels: List<LabelInfoApiModel>? = null,
    val createdAt: String? = null,
)

@JsonClass(generateAdapter = true)
data class CreatorViewerApiModel(
    val muted: Boolean? = null,
    val mutedByList: MuteBlockListApiModel? = null,
    val blockedBy: Boolean? = null,
    val blocking: String? = null,
    val blockingByList: MuteBlockListApiModel? = null,
    val following: String? = null,
    val followedBy: String? = null,
    val knownFollowers: KnownFollowersApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class MuteBlockListApiModel(
    val uri: String? = null,
    val cid: String? = null,
    val name: String? = null,
    val purpose: String? = null,
    val avatar: String? = null,
    val listItemCount: Int? = null,
    val labels: List<LabelInfoApiModel>? = null,
    val viewer: ListViewerApiModel? = null,
    val indexedAt: String? = null,
)

@JsonClass(generateAdapter = true)
data class ListViewerApiModel(
    val muted: Boolean? = null,
    val blocked: String? = null,
)

@JsonClass(generateAdapter = true)
data class KnownFollowersApiModel(
    val count: Int? = null,
    val followers: List<ProfileMinimalApiModel?>,
)

@JsonClass(generateAdapter = true)
data class ProfileMinimalApiModel(
    val did: String? = null,
    val handle: String? = null,
    val displayName: String? = null,
    val avatar: String? = null,
    val associated: AssociatedDataApiModel? = null,
    val labels: List<LabelInfoApiModel>? = null,
    val createdAt: String? = null,
)

@JsonClass(generateAdapter = true)
data class LabelInfoApiModel(
    val ver: Int? = null,
    val src: String? = null,
    val uri: String? = null,
    val cid: String? = null,
    val `val`: String? = null,
    val neg: Boolean? = null,
    val cts: String? = null,
    val exp: String? = null,
    val sig: String? = null,
)

@JsonClass(generateAdapter = true)
data class ProfileViewerApiModel(
    val muted: Boolean? = null,
    val mutedByList: MuteBlockListApiModel? = null,
    val blockedBy: Boolean? = null,
    val blocking: String? = null,
    val blockingByList: MuteBlockListApiModel? = null,
    val following: String? = null,
    val followedBy: String? = null,
    val knownFollowers: KnownFollowersApiModel? = null,
)

@JsonClass(generateAdapter = true)
data class PinnedPostApiModel(
    val uri: String? = null,
    val cid: String? = null,
)