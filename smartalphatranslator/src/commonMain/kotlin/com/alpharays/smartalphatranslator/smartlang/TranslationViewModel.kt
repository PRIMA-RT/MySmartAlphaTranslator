/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

import androidx.lifecycle.ViewModel
import com.alpharays.smartalphatranslator.smartlang.TranslationUtils.generateStableHash
import com.alpharays.smartalphatranslator.smartlang.data.TranslationRepository
import com.alpharays.smartalphatranslator.smartlang.local.entity.TranslationCacheEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TranslationViewModel(
    private val repository: TranslationRepository
) : ViewModel() {

    private val _language = MutableStateFlow("Hindi")
    val language = _language.asStateFlow()

    private val _translatorModel: MutableStateFlow<TranslatorModel> =
        MutableStateFlow(TranslatorModel.GoogleMlKit)

    private val memoryCache = mutableMapOf<String, String>()

    fun setLanguage(lang: String) {
        _language.value = lang
    }

    fun setTranslatorModel(model: TranslatorModel) {
        _translatorModel.value = model
    }

    suspend fun translate(text: String): String {
        if (text.isEmpty()) return text

        val key = generateStableHash("${_language.value}:${_translatorModel.value.name}:$text")

        memoryCache[key]?.let { return it }

        repository.getCachedTranslation(key)?.let { cached ->
            memoryCache[key] = cached
            return cached
        }

        val translated = if (_translatorModel.value == TranslatorModel.OpenAi) {
            PlatformTranslator.translateWithOpenAI(text, _language.value)
        } else {
            PlatformTranslator.translateWithMlKit(text, _language.value)
        }

        memoryCache[key] = translated
        repository.saveTranslation(
            TranslationCacheEntity(
                hashId = key,
                sourceText = text,
                translatedText = translated,
                language = _language.value,
                model = _translatorModel.value.name
            )
        )

        return translated
    }
}


sealed class TranslatorModel(val name: String) {
    object OpenAi : TranslatorModel("OpenAI")
    object GoogleMlKit : TranslatorModel("GoogleMlKit")
}
