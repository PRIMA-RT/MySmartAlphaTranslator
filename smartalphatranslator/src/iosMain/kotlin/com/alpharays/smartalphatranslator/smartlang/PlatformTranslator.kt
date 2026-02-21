/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * iOS implementation.
 * ML Kit is not available on iOS — returns original text.
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        println("AlphaLangLogging iOS ML Kit not available, returning original text")
        return text
    }
}
