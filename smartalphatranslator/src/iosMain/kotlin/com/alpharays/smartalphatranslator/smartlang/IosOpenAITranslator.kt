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
 * iOS implementation of OpenAI translator using Ktor + Darwin engine.
 */
object IosOpenAITranslator {
    private const val OPENAI_API_KEY = ""
    private const val OPENAI_URL = "https://api.openai.com/v1/chat/completions"

    private val client = HttpClient(Darwin)
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun translate(text: String, targetLang: String): String = withContext(Dispatchers.Default) {
        println("AlphaLangLogging [iOS-OpenAI] Translating '$text' to $targetLang")

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
                put("model", "gpt-4o-mini")
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

            println("AlphaLangLogging [iOS-OpenAI] Request Body: $requestBody")

            val response = client.post(OPENAI_URL) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $OPENAI_API_KEY")
                setBody(requestBody.toString())
            }

            println("AlphaLangLogging [iOS-OpenAI] Response Code: ${response.status.value}")

            if (!response.status.isSuccess()) {
                println("AlphaLangLogging [iOS-OpenAI] Error Response Code: ${response.status.value}")
                return@withContext text
            }

            val bodyStr = response.bodyAsText()
            println("AlphaLangLogging [iOS-OpenAI] Response Body: $bodyStr")

            val jsonResponse = json.parseToJsonElement(bodyStr).jsonObject
            val translated = jsonResponse["choices"]!!
                .jsonArray[0]
                .jsonObject["message"]!!
                .jsonObject["content"]!!
                .jsonPrimitive.content
                .trim()

            println("AlphaLangLogging [iOS-OpenAI] Translation Result: $translated")
            return@withContext translated
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging [iOS-OpenAI] Error translating '$text': ${e.message}")
            return@withContext text
        }
    }
}
