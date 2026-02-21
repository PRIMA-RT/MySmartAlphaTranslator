package com.alpharays.mysmartalphatranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.alpharays.smartalphatranslator.smartlang.LocalTranslator
import com.alpharays.smartalphatranslator.smartlang.TranslationViewModel
import com.alpharays.smartalphatranslator.smartlang.TranslatorProvider
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val translationViewModel: TranslationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val translatorProvider = TranslatorProvider(
            currentLanguage = translationViewModel.language,
            translate = { text -> translationViewModel.translate(text) }
        )

        setContent {
            CompositionLocalProvider(LocalTranslator provides translatorProvider) {
                App(translationViewModel)
            }
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    // Preview not available — App requires TranslationViewModel
}