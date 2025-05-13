package com.example.crushtestwork.di

import com.example.crushtestwork.data.di.networkingModule
import com.example.crushtestwork.presentation.common.di.useCaseModule
import com.example.crushtestwork.presentation.common.di.viewModelModule
import org.koin.dsl.module

val testWorkModule = module {
    // data
    includes(networkingModule)

    // presentation
    includes(useCaseModule, viewModelModule)
}