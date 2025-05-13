package com.example.crushtestwork.presentation.features.add_recording

import androidx.lifecycle.viewModelScope
import com.example.crushtestwork.domain.core.onFail
import com.example.crushtestwork.domain.core.onSuccess
import com.example.crushtestwork.domain.model.RecordingItem
import com.example.crushtestwork.domain.usecase.AddRecordingUseCase
import com.example.crushtestwork.presentation.common.base.BaseViewModel
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenEffect
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenEvent
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenState
import kotlinx.coroutines.launch

class AddRecordingScreenViewModel(
    private val addRecordingUseCase: AddRecordingUseCase
): BaseViewModel<AddRecordingScreenState, AddRecordingScreenEvent, AddRecordingScreenEffect>(
    initialState = AddRecordingScreenState(
        "", ""
    )
) {

    override fun obtainEvent(event: AddRecordingScreenEvent) {
        when (event) {
            AddRecordingScreenEvent.OnCreteClicked -> addRecordingAndGoBack()
            is AddRecordingScreenEvent.OnDescriptionInput -> inputDescription(event.text)
            is AddRecordingScreenEvent.OnTitleInput -> inputTitle(event.text)
        }
    }

    private fun inputTitle(text: String) {
        updateState {
            copy(
                title = text
            )
        }
    }

    private fun inputDescription(text: String) {
        updateState {
            copy(
                description = text
            )
        }
    }

    private fun addRecordingAndGoBack() = viewModelScope.launch {
        doLoading(true)
        addRecordingUseCase(
            RecordingItem(
                id = null,
                title = viewState.title,
                content = viewState.description
            )
        ).onSuccess {
            makeGoBackEffect()
        }.onFail {
            makeError(it.name)
        }
        doLoading(false)
    }

    private fun makeError(error: String?) {
        updateState {
            copy(
                isError = error
            )
        }
    }

    private fun doLoading(isLoading: Boolean) {
        updateState {
            copy(
                isLoading = isLoading
            )
        }
    }

    private fun makeGoBackEffect() = viewModelScope.launch {
        emitEffect(AddRecordingScreenEffect.GoBack)
    }

}