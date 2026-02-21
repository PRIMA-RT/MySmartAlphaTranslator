package com.alpharays.mysmartalphatranslator

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpharays.smartalphatranslator.smartlang.SmartAutoText
import com.alpharays.smartalphatranslator.smartlang.TranslationViewModel
import com.alpharays.smartalphatranslator.smartlang.TranslatorModel

// ── Color palette (matches HomeScreen) ──
private val DeepIndigo = Color(0xFF1A1040)
private val RichPurple = Color(0xFF6C3CE0)
private val VividViolet = Color(0xFF9B5DFF)
private val ElectricBlue = Color(0xFF4FC3F7)
private val MintGreen = Color(0xFF00E5A0)
private val SoftWhite = Color(0xFFF5F5FF)
private val CardDark = Color(0xFF241560)
private val CardBorder = Color(0xFF7E57FF)
private val Coral = Color(0xFFFF6B6B)

private val supportedLanguages = listOf(
    "Hindi", "Spanish", "French", "German", "Japanese",
    "Chinese", "Korean", "Arabic", "Portuguese", "Russian",
    "Italian", "Bengali", "Turkish", "Tamil", "Telugu"
)

private val sampleTexts = listOf(
    "Hello! Welcome to Smart Alpha Translator.",
    "This library translates text at runtime for you.",
    "Compose Multiplatform makes cross-platform easy.",
    "Choose any language and see instant translation.",
    "Built with ML Kit and OpenAI for best results."
)

@Composable
fun TranslationPlaygroundScreen(
    translationViewModel: TranslationViewModel,
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val currentLanguage by translationViewModel.language.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var selectedModel by remember { mutableStateOf<TranslatorModel>(TranslatorModel.GoogleMlKit) }
    var isLanguageDropdownExpanded by remember { mutableStateOf(false) }

    // Animate appearance
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // Apply model changes
    LaunchedEffect(selectedModel) {
        translationViewModel.setTranslatorModel(selectedModel)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DeepIndigo, Color(0xFF0D0821))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .safeContentPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Top bar ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { -20 }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .clickable { onNavigateBack() },
                        shape = CircleShape,
                        color = CardDark.copy(alpha = 0.7f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "←",
                                color = SoftWhite,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        SmartAutoText(
                            text = "Translation Playground",
                            color = SoftWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            autoTranslate = false
                        )
                        SmartAutoText(
                            text = "Try SmartAlphaTranslator live",
                            color = SoftWhite.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            autoTranslate = false
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Language Selector ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, delayMillis = 100)) + slideInVertically(tween(500, delayMillis = 100)) { 40 }
            ) {
                Column {
                    SmartAutoText(
                        text = "Target Language",
                        color = SoftWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isLanguageDropdownExpanded = true }
                                .border(
                                    width = 1.dp,
                                    color = CardBorder.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(14.dp)
                                ),
                            shape = RoundedCornerShape(14.dp),
                            color = CardDark.copy(alpha = 0.6f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🌐  $currentLanguage",
                                    color = SoftWhite,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "▼",
                                    color = VividViolet,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = isLanguageDropdownExpanded,
                            onDismissRequest = { isLanguageDropdownExpanded = false },
                            modifier = Modifier
                                .background(CardDark)
                                .heightIn(max = 300.dp)
                        ) {
                            supportedLanguages.forEach { lang ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = lang,
                                            color = if (lang == currentLanguage) VividViolet else SoftWhite,
                                            fontWeight = if (lang == currentLanguage) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        translationViewModel.setLanguage(lang)
                                        isLanguageDropdownExpanded = false
                                    },
                                    leadingIcon = {
                                        if (lang == currentLanguage) {
                                            Text("✓", color = MintGreen, fontSize = 14.sp)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Model Selector ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, delayMillis = 200)) + slideInVertically(tween(500, delayMillis = 200)) { 40 }
            ) {
                Column {
                    SmartAutoText(
                        text = "Translation Engine",
                        color = SoftWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Offline row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ModelChip(
                            label = "ML Kit",
                            emoji = "🧠",
                            subtitle = "Offline",
                            isSelected = selectedModel == TranslatorModel.GoogleMlKit,
                            onClick = { selectedModel = TranslatorModel.GoogleMlKit },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Cloud section label
                    SmartAutoText(
                        text = "Cloud Engines",
                        color = SoftWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Cloud row — OpenAI + OpenRouter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ModelChip(
                            label = "OpenAI",
                            emoji = "🤖",
                            subtitle = "Cloud",
                            isSelected = selectedModel == TranslatorModel.OpenAi,
                            onClick = { selectedModel = TranslatorModel.OpenAi },
                            modifier = Modifier.weight(1f)
                        )
                        ModelChip(
                            label = "OpenRouter",
                            emoji = "🌐",
                            subtitle = "Cloud",
                            isSelected = selectedModel == TranslatorModel.OpenRouter,
                            onClick = { selectedModel = TranslatorModel.OpenRouter },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Input area ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, delayMillis = 300)) + slideInVertically(tween(500, delayMillis = 300)) { 40 }
            ) {
                Column {
                    SmartAutoText(
                        text = "Your Text (English)",
                        color = SoftWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = CardBorder.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        color = CardDark.copy(alpha = 0.6f)
                    ) {
                        Box(modifier = Modifier.padding(20.dp)) {
                            if (inputText.isEmpty()) {
                                Text(
                                    text = "Type something to translate…",
                                    color = SoftWhite.copy(alpha = 0.3f),
                                    fontSize = 15.sp
                                )
                            }
                            BasicTextField(
                                value = inputText,
                                onValueChange = { inputText = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 80.dp),
                                textStyle = TextStyle(
                                    color = SoftWhite,
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp
                                ),
                                cursorBrush = SolidColor(VividViolet)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Translation output ──
            AnimatedVisibility(
                visible = visible && inputText.isNotEmpty(),
                enter = fadeIn(tween(300)) + expandVertically(tween(300)),
                exit = fadeOut(tween(200)) + shrinkVertically(tween(200))
            ) {
                Column {
                    SmartAutoText(
                        text = "Translated Output ($currentLanguage)",
                        color = MintGreen.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(MintGreen.copy(alpha = 0.3f), ElectricBlue.copy(alpha = 0.3f))
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        color = CardDark.copy(alpha = 0.4f)
                    ) {
                        Box(modifier = Modifier.padding(20.dp)) {
                            SmartAutoText(
                                text = inputText,
                                color = MintGreen,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Quick phrases ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, delayMillis = 450)) + slideInVertically(tween(500, delayMillis = 450)) { 40 }
            ) {
                Column {
                    SmartAutoText(
                        text = "Quick Phrases",
                        color = SoftWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    sampleTexts.forEach { sample ->
                        SamplePhraseChip(
                            text = sample,
                            onClick = { inputText = sample }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Live demo section ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, delayMillis = 600)) + slideInVertically(tween(500, delayMillis = 600)) { 40 }
            ) {
                Column {
                    SmartAutoText(
                        text = "Live SmartAutoText Demo",
                        color = SoftWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        autoTranslate = false
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(RichPurple.copy(alpha = 0.4f), ElectricBlue.copy(alpha = 0.4f))
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        color = CardDark.copy(alpha = 0.45f)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SmartAutoText(
                                text = "Every text below uses SmartAutoText composable",
                                color = VividViolet,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                autoTranslate = false
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            // All these use SmartAutoText and will auto-translate
                            DemoRow(
                                label = "Greeting:",
                                text = "Good morning! How are you today?"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            DemoRow(
                                label = "Action:",
                                text = "Tap the button to continue"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            DemoRow(
                                label = "Status:",
                                text = "Your translation is ready"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            DemoRow(
                                label = "Error:",
                                text = "Something went wrong. Please try again."
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun ModelChip(
    label: String,
    emoji: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(
        if (isSelected) VividViolet else CardBorder.copy(alpha = 0.2f),
        tween(250)
    )
    val bgAlpha by animateFloatAsState(
        if (isSelected) 0.8f else 0.4f,
        tween(250)
    )

    Surface(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        color = CardDark.copy(alpha = bgAlpha)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 26.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                color = if (isSelected) SoftWhite else SoftWhite.copy(alpha = 0.6f),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = if (isSelected) VividViolet else SoftWhite.copy(alpha = 0.35f),
                fontSize = 11.sp
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MintGreen)
                )
            }
        }
    }
}

@Composable
private fun SamplePhraseChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = CardBorder.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        color = CardDark.copy(alpha = 0.35f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "💬", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = SoftWhite.copy(alpha = 0.7f),
                fontSize = 13.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "→",
                color = VividViolet,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun DemoRow(label: String, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = ElectricBlue.copy(alpha = 0.6f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(64.dp)
        )
        SmartAutoText(
            text = text,
            color = SoftWhite.copy(alpha = 0.85f),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}
