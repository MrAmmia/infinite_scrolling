package net.thebookofcode.www.infinitescrolling.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.flow.*
import net.thebookofcode.www.infinitescrolling.PixabayApi
import net.thebookofcode.www.infinitescrolling.model.Photo
import net.thebookofcode.www.infinitescrolling.model.PhotoList
import net.thebookofcode.www.infinitescrolling.util.Constants.Companion.NETWORK_PAGE_SIZE
import net.thebookofcode.www.infinitescrolling.util.DataState
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val api: PixabayApi
) {
    fun getPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PixaBayPagingSource(api) }
        ).flow
    }

    suspend fun getItDone(): Flow<DataState<PagingData<Photo>>> = flow {
        emit(DataState.Loading)
        try {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { PixaBayPagingSource(api) }
            ).flow
            pager.collect{
                emit(DataState.Success(it))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}