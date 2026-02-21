/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object OpenRouterTranslator {
    private const val OPENROUTER_API_KEY =
        "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"
    private const val OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions"

    suspend fun translate(text: String, targetLang: String): String = withContext(Dispatchers.IO) {
        println("AlphaLangLogging [OpenRouter] Translating '$text' to $targetLang")

        try {
            val prompt = """
    Translate the following text into $targetLang.
    The translation should be natural, concise, and easy to understand — suitable for mobile app UI.
    Keep the translation approximately the same length and number of words as the original text.
    Do not include any explanations or additional comments.
    For Indian languages, use a natural mixed style (e.g., Hinglish or similar blends common in daily use).
    For non-Indian languages (e.g., Japanese, Chinese, etc.), translate directly and naturally.
    Use simple, everyday language that feels conversational and user-friendly.
    Text: $text
""".trimIndent()


            // Build messages array
            val messages = JSONArray()
                .put(
                    JSONObject()
                        .put("role", "user")
                        .put("content", prompt)
                )

            val requestBody = JSONObject()
                .put("model", "openai/gpt-oss-120b")
                .put("messages", messages)
                .toString()

            val request = Request.Builder()
                .url(OPENROUTER_URL)
                .header("Authorization", "Bearer $OPENROUTER_API_KEY")
                .header("Content-Type", "application/json")
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .build()

            println("AlphaLangLogging [OpenRouter] Request Body: $requestBody")

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("AlphaLangLogging [OpenRouter] Error Response Code: ${response.code}")
                    return@withContext text
                }

                val bodyStr = response.body?.string()
                println("AlphaLangLogging [OpenRouter] Response Code: ${response.code}")
                println("AlphaLangLogging [OpenRouter] Response Body: $bodyStr")

                if (bodyStr == null) {
                    println("AlphaLangLogging [OpenRouter] Error: empty response body")
                    return@withContext text
                }

                val json = JSONObject(bodyStr)
                val translated = json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim()

                println("AlphaLangLogging [OpenRouter] Translation Result: $translated")
                return@withContext translated
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging [OpenRouter] Error translating '$text': ${e.message}")
            return@withContext text
        }
    }

}
