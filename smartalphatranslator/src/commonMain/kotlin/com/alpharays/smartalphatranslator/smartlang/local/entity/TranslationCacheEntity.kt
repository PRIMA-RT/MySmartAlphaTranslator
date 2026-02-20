package com.alpharays.smartalphatranslator.smartlang.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */
@Entity(tableName = "translation_cache")
data class TranslationCacheEntity(
    @PrimaryKey val hashId: String,   // unique key for language+model+text
    val sourceText: String,
    val translatedText: String,
    val language: String,
    val model: String
)
