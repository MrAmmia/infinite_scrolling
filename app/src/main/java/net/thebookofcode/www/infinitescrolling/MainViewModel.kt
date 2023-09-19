package net.thebookofcode.www.infinitescrolling

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.thebookofcode.www.infinitescrolling.data.MainRepository
import net.thebookofcode.www.infinitescrolling.model.Photo
import net.thebookofcode.www.infinitescrolling.util.DataState
import javax.inject.Inject

data class ViewModelState(
    val isLoading: Boolean = false,
    val photos: PagingData<Photo> = PagingData.empty(),
    val error: String? = null
) {
    fun toUiState(): HomeUiState =
        if (photos == null) {
            HomeUiState.NoPhotos(
                isLoading = isLoading,
                errorMessage = error!!
            )
        } else {
            HomeUiState.HasPhotos(
                photos = photos,
                isLoading = isLoading,
                errorMessage = null
            )
        }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _viewModelState = MutableStateFlow(ViewModelState())
    val viewModelState: LiveData<ViewModelState> = _viewModelState.asLiveData()

    private val _dataState: MutableLiveData<DataState<PagingData<Photo>>> = MutableLiveData()
    val dataState: LiveData<DataState<PagingData<Photo>>>
        get() = _dataState

    val uiState = _viewModelState
        .map(ViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _viewModelState.value.toUiState()
        )


    init {
       getPhotos()
        // getItDone()
    }

    private fun getPhotos() {
        _viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                repository.getPhotos()
                    .cachedIn(viewModelScope)
                    .collect { results ->
                        _viewModelState.update { it.copy(photos = results) }
                    }
            } catch (e: Exception) {
                _viewModelState.update { it.copy(error = e.message) }
            } finally {
                _viewModelState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun getItDone(){
        viewModelScope.launch {
            repository.getItDone().onEach {dataState ->
                _dataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}