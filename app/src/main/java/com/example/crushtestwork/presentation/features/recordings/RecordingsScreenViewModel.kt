package com.example.crushtestwork.presentation.features.recordings

import androidx.lifecycle.viewModelScope
import com.example.crushtestwork.domain.core.onSuccess
import com.example.crushtestwork.domain.usecase.AddRecordingUseCase
import com.example.crushtestwork.domain.usecase.GetRecordingsListUseCase
import com.example.crushtestwork.presentation.common.base.BaseViewModel
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenEffect
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenEvent
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import com.example.crushtestwork.domain.model.RecordingItem
import com.example.crushtestwork.domain.usecase.DeleteRecordingUseCase

// using MVI Presentation layer architecture. (MVVM on steroids :), easier to write unit test,refactor and debug )
class RecordingsScreenViewModel(
    private val getRecordingsListUseCase: GetRecordingsListUseCase,
    private val deleteRecordingUseCase: DeleteRecordingUseCase
) : BaseViewModel<RecordingsScreenState, RecordingsScreenEvent, RecordingsScreenEffect>(
        initialState = RecordingsScreenState()
    ) {
    init {
        loadRecordings()
    }

    override fun obtainEvent(event: RecordingsScreenEvent) {
        when (event) {
            RecordingsScreenEvent.OnAddRecordingClicked -> makeOpenCreateNewRecordingScreenEffect()
            is RecordingsScreenEvent.OnModifyRecordingClicked -> makeOpenModifyRecordingScreenEffect(event.id)
            RecordingsScreenEvent.OnRefresh -> loadRecordings()
            is RecordingsScreenEvent.OnDeleteRecordingClicked -> deleteRecording(event.id)
        }
    }

    private fun loadRecordings() = viewModelScope.launch {
        doLoading(true)
        getRecordingsListUseCase().onSuccess {
            updateState {
                copy(
                    listOfRecordings = it.toPersistentList()
                )
            }
        }
        doLoading(false)
    }

    private fun deleteRecording(id: String) = viewModelScope.launch {
        deleteRecordingUseCase(id).onSuccess {
            loadRecordings() // to reload
        }
    }

    private fun doLoading(isLoading: Boolean) {
        updateState {
            copy(
                isLoading = isLoading
            )
        }
    }

    private fun makeOpenCreateNewRecordingScreenEffect() = viewModelScope.launch {
        emitEffect(RecordingsScreenEffect.OpenCreateNewRecordingScreen)
    }

    private fun makeOpenModifyRecordingScreenEffect(id: String) = viewModelScope.launch {
        emitEffect(RecordingsScreenEffect.OpenModifyRecordingScreen(id))
    }

}