/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.mysmartalphatranslator.smartlang

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
}
