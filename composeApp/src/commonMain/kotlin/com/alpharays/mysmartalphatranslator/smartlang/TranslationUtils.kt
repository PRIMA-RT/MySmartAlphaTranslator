package com.alpharays.mysmartalphatranslator.smartlang

import kotlin.experimental.and

object TranslationUtils {

    /**
     * Generates a stable hash for translation cache keys.
     * Uses a simple but effective hash algorithm that works across all Kotlin targets.
     */
    fun generateStableHash(input: String): String {
        val normalized = input.trim().replace("\r\n", "\n")
        val bytes = normalized.encodeToByteArray()

        // FNV-1a 64-bit hash — deterministic, fast, and multiplatform
        var hash = -3750763034362895579L // FNV offset basis (unsigned: 14695981039346656037)
        for (b in bytes) {
            hash = hash xor (b.toLong() and 0xFF)
            hash *= 1099511628211L // FNV prime
        }

        // Convert to hex string
        return buildString {
            for (i in 56 downTo 0 step 8) {
                val byte = (hash shr i) and 0xFF
                append(byte.toString(16).padStart(2, '0'))
            }
        }
    }
}