package com.alpharays.smartalphatranslator.smartlang

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

/**
 * Multiplatform OpenAI translator using Ktor.
 * Works on Android (OkHttp engine) and iOS (Darwin engine) — engine auto-resolved.
 */
object OpenAITranslator {
    private const val OPENAI_URL = "https://api.openai.com/v1/chat/completions"

    private val client = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun translate(text: String, targetLang: String): String {
        val apiKey = SmartTranslatorConfig.openAiApiKey
        if (apiKey.isEmpty()) {
            println("AlphaLangLogging [OpenAI] No API key configured — call SmartTranslatorConfig.init() first")
            return text
        }
        println("AlphaLangLogging [OpenAI] Translating '$text' to $targetLang")

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
                put("model", SmartTranslatorConfig.openAiModel)
                putJsonArray("messages") {
                    addJsonObject {
                        put("role", "developer")
                        put(
                            "content",
                            "You are a helpful and context-aware assistant that translates text into natural, concise, and easy-to-understand language suitable for mobile app UI. Use simple, user-friendly wording that feels conversational and fits naturally within buttons, labels, and messages."
                        )
                    }
                    addJsonObject {
                        put("role", "user")
                        put("content", prompt)
                    }
                }
            }

            println("AlphaLangLogging [OpenAI] Request Body: $requestBody")

            val response = client.post(OPENAI_URL) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
                setBody(requestBody.toString())
            }

            println("AlphaLangLogging [OpenAI] Response Code: ${response.status.value}")

            if (!response.status.isSuccess()) {
                println("AlphaLangLogging [OpenAI] Error Response Code: ${response.status.value}")
                return text
            }

            val bodyStr = response.bodyAsText()
            println("AlphaLangLogging [OpenAI] Response Body: $bodyStr")

            val jsonResponse = json.parseToJsonElement(bodyStr).jsonObject
            val translated = jsonResponse["choices"]!!
                .jsonArray[0]
                .jsonObject["message"]!!
                .jsonObject["content"]!!
                .jsonPrimitive.content
                .trim()

            println("AlphaLangLogging [OpenAI] Translation Result: $translated")
            return translated
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging [OpenAI] Error translating '$text': ${e.message}")
            return text
        }
    }
}
