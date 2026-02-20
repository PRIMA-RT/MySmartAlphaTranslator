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

object OpenAITranslator {
    private const val OPENAI_API_KEY =
        ""
    private const val OPENAI_URL = "https://api.openai.com/v1/chat/completions"

    suspend fun translate(text: String, targetLang: String): String = withContext(Dispatchers.IO) {
        println("AlphaLangLogging Translating '$text' to $targetLang")

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


            // Build messages array similar to Postman
            val messages = JSONArray()
                .put(
                    JSONObject()
                        .put("role", "developer")
                        .put(
                            "content",
                            "You are a helpful and context-aware assistant that translates text into natural, concise, and easy-to-understand language suitable for mobile app UI. Use simple, user-friendly wording that feels conversational and fits naturally within buttons, labels, and messages."
                        )
                )
                .put(JSONObject().put("role", "user").put("content", prompt))

            val requestBody = JSONObject()
                .put("model", "gpt-4o-mini")
                .put("messages", messages)
                .toString()

            val request = Request.Builder()
                .url(OPENAI_URL)
                .header("Authorization", "Bearer $OPENAI_API_KEY")
                .header("Content-Type", "application/json")
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .build()

            println("AlphaLangLoggingRequest Body: $requestBody")

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("AlphaLangLoggingError Response Code: ${response.code}")
                    return@withContext text
                }


                val bodyStr = response.body?.string()
                println("AlphaLangLoggingResponse Code: ${response.code}")
                println("AlphaLangLoggingResponse Body: $bodyStr")

                if (!response.isSuccessful || bodyStr == null) {
                    println("AlphaLangLoggingError Response: ${response.message}")
                    return@withContext text
                }

                val json = JSONObject(bodyStr)
                val translated = json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim()

                println("AlphaLangLogging Translation Result: $translated")
                return@withContext translated
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging Error translating '$text': ${e.message}")
            return@withContext text
        }
    }

}
