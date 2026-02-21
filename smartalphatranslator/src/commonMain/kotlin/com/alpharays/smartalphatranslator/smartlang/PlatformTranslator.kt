/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * Platform-specific translator implementations.
 * Only ML Kit is platform-specific (Android-only).
 * OpenAI and OpenRouter are implemented in commonMain via Ktor.
 */
expect object PlatformTranslator {
    suspend fun translateWithMlKit(text: String, targetLang: String): String
}
