/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * iOS implementation.
 * ML Kit: Not available on iOS — returns original text.
 * OpenAI & OpenRouter: Implemented via Ktor + Darwin HTTP engine.
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        // ML Kit is Android-only; on iOS this is a no-op stub
        println("AlphaLangLogging iOS ML Kit not available, returning original text")
        return text
    }

    actual suspend fun translateWithOpenAI(text: String, targetLang: String): String {
        return IosOpenAITranslator.translate(text, targetLang)
    }

    actual suspend fun translateWithOpenRouter(text: String, targetLang: String): String {
        return IosOpenRouterTranslator.translate(text, targetLang)
    }
}
