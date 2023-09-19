package net.thebookofcode.www.infinitescrolling.model

import com.google.gson.annotations.SerializedName

data class PhotoList(
    @SerializedName("hits")
    val photos: List<Photo>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)
