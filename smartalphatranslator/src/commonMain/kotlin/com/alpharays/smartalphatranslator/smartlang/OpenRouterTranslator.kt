package com.alpharays.smartalphatranslator.smartlang

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

/**
 * Multiplatform OpenRouter translator using Ktor.
 * Works on Android (OkHttp engine) and iOS (Darwin engine) — engine auto-resolved.
 */
object OpenRouterTranslator {
    private const val OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions"

    private val client = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun translate(text: String, targetLang: String): String {
        val apiKey = SmartTranslatorConfig.openRouterApiKey
        if (apiKey.isEmpty()) {
            println("AlphaLangLogging [OpenRouter] No API key configured — call SmartTranslatorConfig.init() first")
            return text
        }
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

            val requestBody = buildJsonObject {
                put("model", SmartTranslatorConfig.openRouterModel)
                putJsonArray("messages") {
                    addJsonObject {
                        put("role", "user")
                        put("content", prompt)
                    }
                }
            }

            println("AlphaLangLogging [OpenRouter] Request Body: $requestBody")

            val response = client.post(OPENROUTER_URL) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
                setBody(requestBody.toString())
            }

            println("AlphaLangLogging [OpenRouter] Response Code: ${response.status.value}")

            if (!response.status.isSuccess()) {
                println("AlphaLangLogging [OpenRouter] Error Response Code: ${response.status.value}")
                return text
            }

            val bodyStr = response.bodyAsText()
            println("AlphaLangLogging [OpenRouter] Response Body: $bodyStr")

            val jsonResponse = json.parseToJsonElement(bodyStr).jsonObject
            val translated = jsonResponse["choices"]!!
                .jsonArray[0]
                .jsonObject["message"]!!
                .jsonObject["content"]!!
                .jsonPrimitive.content
                .trim()

            println("AlphaLangLogging [OpenRouter] Translation Result: $translated")
            return translated
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging [OpenRouter] Error translating '$text': ${e.message}")
            return text
        }
    }
}
