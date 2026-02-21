package com.alpharays.smartalphatranslator.smartlang

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*

/**
 * iOS implementation of OpenRouter translator using Ktor + Darwin engine.
 */
object IosOpenRouterTranslator {
    private const val OPENROUTER_API_KEY =
        "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"
    private const val OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions"

    private val client = HttpClient(Darwin)
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun translate(text: String, targetLang: String): String = withContext(Dispatchers.Default) {
        println("AlphaLangLogging [iOS-OpenRouter] Translating '$text' to $targetLang")

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

            val requestBody = buildJsonObject {
                put("model", "openai/gpt-oss-120b")
                putJsonArray("messages") {
                    addJsonObject {
                        put("role", "user")
                        put("content", prompt)
                    }
                }
            }

            println("AlphaLangLogging [iOS-OpenRouter] Request Body: $requestBody")

            val response = client.post(OPENROUTER_URL) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $OPENROUTER_API_KEY")
                setBody(requestBody.toString())
            }

            println("AlphaLangLogging [iOS-OpenRouter] Response Code: ${response.status.value}")

            if (!response.status.isSuccess()) {
                println("AlphaLangLogging [iOS-OpenRouter] Error Response Code: ${response.status.value}")
                return@withContext text
            }

            val bodyStr = response.bodyAsText()
            println("AlphaLangLogging [iOS-OpenRouter] Response Body: $bodyStr")

            val jsonResponse = json.parseToJsonElement(bodyStr).jsonObject
            val translated = jsonResponse["choices"]!!
                .jsonArray[0]
                .jsonObject["message"]!!
                .jsonObject["content"]!!
                .jsonPrimitive.content
                .trim()

            println("AlphaLangLogging [iOS-OpenRouter] Translation Result: $translated")
            return@withContext translated
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging [iOS-OpenRouter] Error translating '$text': ${e.message}")
            return@withContext text
        }
    }
}
