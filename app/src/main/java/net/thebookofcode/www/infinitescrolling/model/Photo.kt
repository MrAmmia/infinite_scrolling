package net.thebookofcode.www.infinitescrolling.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    val userName: String?,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("previewURL")
    val previewURL: String?,
)
