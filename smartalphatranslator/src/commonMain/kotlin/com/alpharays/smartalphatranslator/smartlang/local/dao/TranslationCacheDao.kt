/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.smartalphatranslator.smartlang.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alpharays.smartalphatranslator.smartlang.local.entity.TranslationCacheEntity

@Dao
interface TranslationCacheDao {

    @Query("SELECT translatedText FROM translation_cache WHERE hashId = :hashId")
    suspend fun getTranslation(hashId: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(entity: TranslationCacheEntity)
}
