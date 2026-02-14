/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.mysmartalphatranslator.smartlang

/**
 * JS implementation - stub
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        println("YuLangLogging JS ML Kit not available, returning original text")
        return text
    }

    actual suspend fun translateWithOpenAI(text: String, targetLang: String): String {
        println("YuLangLogging JS OpenAI not yet implemented, returning original text")
        return text
    }
}
