package net.thebookofcode.www.infinitescrolling.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.thebookofcode.www.infinitescrolling.PixabayApi
import net.thebookofcode.www.infinitescrolling.model.Photo
import net.thebookofcode.www.infinitescrolling.util.Constants.Companion.PIXABAY_STARTING_PAGE_INDEX
import retrofit2.HttpException

class PixaBayPagingSource(
    private val api: PixabayApi
) : PagingSource<Int, Photo>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: PIXABAY_STARTING_PAGE_INDEX
        return try {
            val response = api.getPics(position)
            val photos = response.body()!!.photos
            Log.d("Zivi", "Service -> getPhotos: ${photos.size}")
            LoadResult.Page(
                data = photos,
                prevKey = if (position == PIXABAY_STARTING_PAGE_INDEX) null else position,
                nextKey = if (photos.isEmpty()) null else position + 1

            )
        }catch (exception: Exception) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return null
    }
}