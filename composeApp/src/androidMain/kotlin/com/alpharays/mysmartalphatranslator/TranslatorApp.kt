package com.alpharays.mysmartalphatranslator

import android.app.Application
import com.alpharays.mysmartalphatranslator.smartlang.di.platformDatabaseModule
import com.alpharays.mysmartalphatranslator.smartlang.di.translationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslatorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TranslatorApp)
            modules(
                platformDatabaseModule,
                translationModule
            )
        }
    }
}
