package com.alpharays.mysmartalphatranslator

import android.app.Application
import com.alpharays.smartalphatranslator.smartlang.SmartTranslatorConfig
import com.alpharays.smartalphatranslator.smartlang.di.platformDatabaseModule
import com.alpharays.smartalphatranslator.smartlang.di.translationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslatorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Configure SmartAlphaTranslator API keys
        SmartTranslatorConfig.init(
            openAiApiKey = "",                   // your OpenAI key
            openRouterApiKey = ""                // your OpenRouter key
        )

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
