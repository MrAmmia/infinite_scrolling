package net.thebookofcode.www.infinitescrolling

import androidx.paging.PagingData
import net.thebookofcode.www.infinitescrolling.model.Photo

sealed interface HomeUiState{
    val isLoading: Boolean
    val errorMessage: String?

    data class NoPhotos(
        override val isLoading: Boolean,
        override val errorMessage: String
    ) : HomeUiState

    data class HasPhotos(
        val photos: PagingData<Photo>,
        override val isLoading: Boolean,
        override val errorMessage: String?
    ) : HomeUiState
}