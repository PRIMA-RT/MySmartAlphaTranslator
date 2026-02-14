package com.alpharays.mysmartalphatranslator.smartlang

import java.security.MessageDigest

object TranslationUtils {


    fun generateStableHash(input: String): String {
        val normalized = input.trim().replace("\r\n", "\n")
        val bytes = normalized.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

}