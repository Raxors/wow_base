package com.raxors.wowbase.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel<STATE : BaseScreenState> : ViewModel() {

    protected abstract val initialState: STATE

    protected val _state by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<STATE>
        get() = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is CancellationException) {
            return@CoroutineExceptionHandler
        }
        handleError(throwable)
    }

    abstract fun handleError(error: Throwable)
    abstract fun setLoading(isLoading: Boolean)

    protected fun launch(function: suspend () -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            function.invoke()
        }
    }
}