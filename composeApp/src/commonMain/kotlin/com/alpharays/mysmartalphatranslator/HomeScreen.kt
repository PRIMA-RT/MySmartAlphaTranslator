package com.alpharays.mysmartalphatranslator

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpharays.smartalphatranslator.smartlang.SmartAutoText

// ── Color palette ──────────────────────────────────────────────────────
private val DeepIndigo = Color(0xFF1A1040)
private val RichPurple = Color(0xFF6C3CE0)
private val VividViolet = Color(0xFF9B5DFF)
private val ElectricBlue = Color(0xFF4FC3F7)
private val MintGreen = Color(0xFF00E5A0)
private val SoftWhite = Color(0xFFF5F5FF)
private val CardDark = Color(0xFF241560)
private val CardBorder = Color(0xFF7E57FF)

@Composable
fun HomeScreen(
    onNavigateToPlayground: () -> Unit
) {
    val scrollState = rememberScrollState()

    // Infinite shimmer animation for hero badge
    val infiniteTransition = rememberInfiniteTransition()
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Entry animations
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

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
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // ── Hero badge ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { -40 }
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = RichPurple.copy(alpha = 0.25f),
                    modifier = Modifier.border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                VividViolet.copy(alpha = 0.3f),
                                ElectricBlue.copy(alpha = 0.6f),
                                VividViolet.copy(alpha = 0.3f)
                            ),
                            startX = shimmerOffset * 400f,
                            endX = (shimmerOffset + 1f) * 400f
                        ),
                        shape = RoundedCornerShape(50)
                    )
                ) {
                    SmartAutoText(
                        text = "✨ KMP Translation Library",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        color = ElectricBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Title ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(700, delayMillis = 150)) + slideInVertically(tween(700, delayMillis = 150)) { -30 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SmartAutoText(
                        text = "Smart Alpha",
                        color = SoftWhite,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 44.sp
                    )
                    SmartAutoText(
                        text = "Translator",
                        color = VividViolet,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 44.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Subtitle ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(700, delayMillis = 300))
            ) {
                SmartAutoText(
                    text = "Drop-in Compose Multiplatform library that auto-translates your UI text at runtime. Powered by ML Kit & OpenAI.",
                    color = SoftWhite.copy(alpha = 0.7f),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier.widthIn(max = 340.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ── Feature cards ──
            val features = listOf(
                Triple("🔤", "SmartAutoText", "Replace Text() with SmartAutoText() and every string auto-translates to the selected language."),
                Triple("🤖", "Triple Engine", "Choose Google ML Kit for offline speed, OpenAI GPT for contextual accuracy, or OpenRouter for free cloud translation."),
                Triple("💾", "Smart Caching", "Room-backed translation cache with in-memory layer. Translate once, display instantly thereafter."),
                Triple("📱", "Multiplatform", "Works on Android & iOS with a single shared codebase. Compose Multiplatform ready.")
            )

            features.forEachIndexed { index, (icon, title, description) ->
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(600, delayMillis = 400 + index * 120)) +
                            slideInVertically(tween(600, delayMillis = 400 + index * 120)) { 60 }
                ) {
                    FeatureCard(icon = icon, title = title, description = description)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── CTA Button ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600, delayMillis = 900)) + scaleIn(tween(600, delayMillis = 900))
            ) {
                Button(
                    onClick = onNavigateToPlayground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = RichPurple.copy(alpha = 0.4f),
                            spotColor = RichPurple.copy(alpha = 0.4f)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(RichPurple, VividViolet, ElectricBlue)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        SmartAutoText(
                            text = "Try Translation Playground  →",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ── Footer ──
            SmartAutoText(
                text = "Built with ❤\uFE0F by alpharays",
                color = SoftWhite.copy(alpha = 0.35f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FeatureCard(icon: String, title: String, description: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = CardBorder.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        color = CardDark.copy(alpha = 0.55f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                RichPurple.copy(alpha = 0.4f),
                                VividViolet.copy(alpha = 0.2f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                SmartAutoText(
                    text = title,
                    color = SoftWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                SmartAutoText(
                    text = description,
                    color = SoftWhite.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}
