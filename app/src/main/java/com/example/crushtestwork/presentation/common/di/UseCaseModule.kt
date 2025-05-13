package com.example.crushtestwork.presentation.common.di

import com.example.crushtestwork.domain.usecase.AddRecordingUseCase
import com.example.crushtestwork.domain.usecase.DeleteRecordingUseCase
import com.example.crushtestwork.domain.usecase.GetRecordingsListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetRecordingsListUseCase)
    factoryOf(::AddRecordingUseCase)
    factoryOf(::DeleteRecordingUseCase)
}