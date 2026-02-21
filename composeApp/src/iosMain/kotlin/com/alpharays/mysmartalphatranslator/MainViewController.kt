package com.alpharays.mysmartalphatranslator

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.alpharays.smartalphatranslator.smartlang.LocalTranslator
import com.alpharays.smartalphatranslator.smartlang.SmartTranslatorConfig
import com.alpharays.smartalphatranslator.smartlang.TranslationViewModel
import com.alpharays.smartalphatranslator.smartlang.TranslatorProvider
import com.alpharays.smartalphatranslator.smartlang.di.platformDatabaseModule
import com.alpharays.smartalphatranslator.smartlang.di.translationModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin

fun initKoin() {
    stopKoin() // safe to call even if not started

    // Configure SmartAlphaTranslator API keys
    SmartTranslatorConfig.init(
        openAiApiKey = "",                   // your OpenAI key
        openRouterApiKey = "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"
    )

    startKoin {
        modules(
            platformDatabaseModule,
            translationModule
        )
    }
}

fun MainViewController() = ComposeUIViewController {
    val koin = getKoin()
    val translationViewModel = koin.get<TranslationViewModel>()

    val translatorProvider = TranslatorProvider(
        currentLanguage = translationViewModel.language,
        translate = { text -> translationViewModel.translate(text) }
    )

    CompositionLocalProvider(LocalTranslator provides translatorProvider) {
        App(translationViewModel)
    }
}