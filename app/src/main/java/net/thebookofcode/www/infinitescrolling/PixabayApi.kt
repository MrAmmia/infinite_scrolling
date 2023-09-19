package net.thebookofcode.www.infinitescrolling

import net.thebookofcode.www.infinitescrolling.model.PhotoList
import net.thebookofcode.www.infinitescrolling.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api/${API_KEY}")
    suspend fun getPics(
        @Query("page") page: Int): Response<PhotoList>
}