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
            openRouterApiKey = "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"
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
