/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * Platform-specific translator implementations.
 * On Android: GoogleMlKitTranslator + OpenAITranslator + OpenRouterTranslator
 * On other platforms: stub / alternative implementations
 */
expect object PlatformTranslator {
    suspend fun translateWithMlKit(text: String, targetLang: String): String
    suspend fun translateWithOpenAI(text: String, targetLang: String): String
    suspend fun translateWithOpenRouter(text: String, targetLang: String): String
}
