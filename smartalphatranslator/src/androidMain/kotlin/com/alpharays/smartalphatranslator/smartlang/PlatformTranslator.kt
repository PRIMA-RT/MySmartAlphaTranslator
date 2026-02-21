/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * Android implementation using Google ML Kit and OpenAI
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        return GoogleMlKitTranslator.translate(text, targetLang)
    }

    actual suspend fun translateWithOpenAI(text: String, targetLang: String): String {
        return OpenAITranslator.translate(text, targetLang)
    }

    actual suspend fun translateWithOpenRouter(text: String, targetLang: String): String {
        return OpenRouterTranslator.translate(text, targetLang)
    }
}
