package com.example.crushtestwork.presentation.common.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel <STATE, EVENT, EFFECT> (
    initialState: STATE
) : ViewModel() {

    var viewState by mutableStateOf(initialState)
        private set

    private val _effects = MutableSharedFlow<EFFECT>()
    val effects get() = _effects.asSharedFlow()

    abstract fun obtainEvent(event: EVENT)

    suspend fun emitEffect(effect: EFFECT) {
        _effects.emit(effect)
    }

    protected fun updateState(editor: STATE.() -> STATE) {
        val newState = editor.invoke(viewState)
        viewState = newState
    }
}