package com.alpharays.mysmartalphatranslator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform