/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * JS implementation — ML Kit not available.
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        println("AlphaLangLogging JS ML Kit not available, returning original text")
        return text
    }
}
