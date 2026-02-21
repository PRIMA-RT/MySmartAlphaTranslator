/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * WASM implementation - stub
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        println("AlphaLangLogging WASM ML Kit not available, returning original text")
        return text
    }

    actual suspend fun translateWithOpenAI(text: String, targetLang: String): String {
        println("AlphaLangLogging WASM OpenAI not yet implemented, returning original text")
        return text
    }

    actual suspend fun translateWithOpenRouter(text: String, targetLang: String): String {
        println("AlphaLangLogging WASM OpenRouter not yet implemented, returning original text")
        return text
    }
}
