/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

/**
 * Android implementation using Google ML Kit.
 */
actual object PlatformTranslator {
    actual suspend fun translateWithMlKit(text: String, targetLang: String): String {
        return GoogleMlKitTranslator.translate(text, targetLang)
    }
}
