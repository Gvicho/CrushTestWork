package com.example.crushtestwork

import android.app.Application
import com.example.crushtestwork.di.testWorkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by G.G. 2025
 *
 * I built this app with standards and best practices that I use with big applications.
 * For simple project like this my code might look over-engineering but, I was trying to demonstrate my skills :) <3
 * I don't follow that principles as notions or "must do stuff" with smaller apps
 *
 * */
class CrushTestWorkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    // for di
    private fun initKoin() {
        startKoin{
            androidLogger()
            androidContext(this@CrushTestWorkApplication)
            modules(testWorkModule)
        }
    }
}