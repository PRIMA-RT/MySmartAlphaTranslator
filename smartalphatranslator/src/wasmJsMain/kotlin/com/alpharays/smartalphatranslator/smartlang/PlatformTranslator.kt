/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * WASM implementation — ML Kit not available.
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        println("AlphaLangLogging WASM ML Kit not available, returning original text")
        return text
    }
}
