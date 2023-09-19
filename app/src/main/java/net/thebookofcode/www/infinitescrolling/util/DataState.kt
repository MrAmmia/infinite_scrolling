package net.thebookofcode.www.infinitescrolling.util

sealed class DataState<out R> {
    //data class Loading<T>(val isLoading: Boolean) : DataState<T>()
    object Loading:DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()

    // class Error<T>(val uiComponent: UIComponent) : DataState<T>()
}
/*
sealed class UIComponent{
    data class Toast(val text:String): UIComponent()
}

 */