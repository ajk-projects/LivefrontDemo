package com.example.livefrontdemo.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseApiModel(
    val did: String? = null,
    val didDoc: DidDocApiModel? = null,
    val handle: String? = null,
    val email: String? = null,
    val emailConfirmed: Boolean? = null,
    val emailAuthFactor: Boolean? = null,
    val accessJwt: String? = null,
    val refreshJwt: String? = null,
    val active: Boolean? = null,
)

@JsonClass(generateAdapter = true)
data class AuthRequestApiModel(
    val identifier: String? = null,
    val password: String? = null,
)

@JsonClass(generateAdapter = true)
data class DidDocApiModel(
    val context: List<String> = arrayListOf(),
    val id: String? = null,
    val alsoKnownAs: List<String> = arrayListOf(),
    val verificationMethod: List<VerificationMethodApiModel> = arrayListOf(),
    val service: List<ServiceApiModel> = arrayListOf(),
)

@JsonClass(generateAdapter = true)
data class VerificationMethodApiModel(
    val id: String? = null,
    val type: String? = null,
    val controller: String? = null,
    val publicKeyMultibase: String? = null,
)