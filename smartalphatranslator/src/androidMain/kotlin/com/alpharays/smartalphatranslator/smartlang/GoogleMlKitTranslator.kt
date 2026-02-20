package com.alpharays.smartalphatranslator.smartlang

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

object GoogleMlKitTranslator {

    /**
     * Maps language names to ML Kit language codes
     */
    private fun getLanguageCode(targetLang: String): String {
        return when (targetLang.lowercase()) {
            "hindi" -> TranslateLanguage.HINDI
            "bengali" -> TranslateLanguage.BENGALI
            "tamil" -> TranslateLanguage.TAMIL
            "telugu" -> TranslateLanguage.TELUGU
            "marathi" -> TranslateLanguage.MARATHI
            "gujarati" -> TranslateLanguage.GUJARATI
            "kannada" -> TranslateLanguage.KANNADA
            "urdu" -> TranslateLanguage.URDU
            "japanese" -> TranslateLanguage.JAPANESE
            "chinese", "chinese (simplified)" -> TranslateLanguage.CHINESE
            "spanish" -> TranslateLanguage.SPANISH
            "french" -> TranslateLanguage.FRENCH
            "german" -> TranslateLanguage.GERMAN
            "italian" -> TranslateLanguage.ITALIAN
            "korean" -> TranslateLanguage.KOREAN
            "arabic" -> TranslateLanguage.ARABIC
            "russian" -> TranslateLanguage.RUSSIAN
            "portuguese" -> TranslateLanguage.PORTUGUESE
            "thai" -> TranslateLanguage.THAI
            "vietnamese" -> TranslateLanguage.VIETNAMESE
            "indonesian" -> TranslateLanguage.INDONESIAN
            "turkish" -> TranslateLanguage.TURKISH
            "dutch" -> TranslateLanguage.DUTCH
            "polish" -> TranslateLanguage.POLISH
            "swedish" -> TranslateLanguage.SWEDISH
            "english" -> TranslateLanguage.ENGLISH
            else -> {
                println("AlphaLangLogging Unsupported language: $targetLang, defaulting to English")
                TranslateLanguage.ENGLISH
            }
        }
    }

    suspend fun translate(text: String, targetLang: String): String = withContext(Dispatchers.IO) {
        println("AlphaLangLogging Translating '$text' to $targetLang")

        try {
            val targetLanguageCode = getLanguageCode(targetLang)

            // Create translator options (English to target language)
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(targetLanguageCode)
                .build()

            val translator = Translation.getClient(options)

            // Download model if needed
            val conditions = DownloadConditions.Builder()
                .requireWifi()
                .build()

            println("AlphaLangLogging Ensuring translation model is downloaded for $targetLang")
            translator.downloadModelIfNeeded(conditions).await()

            // Perform translation
            val translatedText = translator.translate(text).await()

            println("AlphaLangLogging Translation Result: $translatedText")

            // Close the translator to free up resources
            translator.close()

            return@withContext translatedText
        } catch (e: Exception) {
            e.printStackTrace()
            println("AlphaLangLogging Error translating '$text': ${e.message}")
            return@withContext text
        }
    }

}
