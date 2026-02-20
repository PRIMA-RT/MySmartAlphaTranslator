/**
 * Created by Sumit Kumar
 * Date: 01/11/25
 */

package com.alpharays.smartalphatranslator.smartlang

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.StateFlow

val LocalTranslator = compositionLocalOf<TranslatorProvider> {
    error("Translator not provided")
}

class TranslatorProvider(
    val currentLanguage: StateFlow<String>,
    val translate: suspend (String) -> String
)
