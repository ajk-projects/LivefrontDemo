package com.example.livefrontdemo.data.api

import com.example.livefrontdemo.data.api.model.AuthRequestApiModel
import com.example.livefrontdemo.data.api.model.AuthResponseApiModel
import com.example.livefrontdemo.data.api.model.FeedResponseApiModel
import com.example.livefrontdemo.data.api.model.PostRequestApiModel
import com.example.livefrontdemo.data.api.model.PostResponseApiModel
import com.example.livefrontdemo.data.api.model.ProfileResponseApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface BlueSkyApi {
    /**
     * Initiate an authentication session, returning an access & refresh JWT token.
     *
     * @param body The request body containing the user's email and password.
     * @return The auth response, including the user's dId, and tokens.
     */
    @Headers(CONTENT_TYPE)
    @POST("$PROTO_SERVER.createSession")
    suspend fun initAuthSession(
        @Body body: AuthRequestApiModel,
    ): Response<AuthResponseApiModel>

    /**
     * Publish a post to the user's timeline.
     *
     * @param body The request body containing the post record.
     * @return The post response, including the post's uri and cid.
     */
    @Headers(CONTENT_TYPE)
    @POST("$PROTO_REPO.createRecord")
    suspend fun publishPost(@Body body: PostRequestApiModel?): Response<PostResponseApiModel>

    /**
     * Get the the timeline for the current signed-in user (based on accessJwt header).
     *
     * @param algorithm Variant 'algorithm' for timeline.
     * @param limit Max number of posts returned. Defaults to 50 upstream.
     * @param cursor Cursor for pagination.
     * @return The feed response, including a list of posts.
     */
    @Headers(CONTENT_TYPE)
    @GET("$BSKY_FEED.getTimeline")
    suspend fun getTimeline(
        @Query("algorithm") algorithm: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("cursor") cursor: String? = null,
    ): Response<FeedResponseApiModel>

    /**
     * Get profile information for a given user.
     *
     * @param actor The user's dId or handle.
     * @return The profile information.
     */
    @Headers(CONTENT_TYPE)
    @GET("$BSKY_ACTOR.getProfile")
    suspend fun getProfile(
        @Query("actor") actor: String,
    ): Response<ProfileResponseApiModel>

    companion object {
        private const val PROTO_SERVER = "com.atproto.server"
        private const val PROTO_REPO = "com.atproto.repo"
        private const val BSKY_FEED = "app.bsky.feed"
        private const val BSKY_ACTOR = "app.bsky.actor"
        private const val CONTENT_TYPE = "Content-Type: application/json"
    }
}