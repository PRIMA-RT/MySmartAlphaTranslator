/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.smartalphatranslator.smartlang.data

import com.alpharays.smartalphatranslator.smartlang.local.dao.TranslationCacheDao
import com.alpharays.smartalphatranslator.smartlang.local.entity.TranslationCacheEntity

class TranslationRepository(
    private val dao: TranslationCacheDao
) {
    suspend fun getCachedTranslation(hashId: String): String? {
        return dao.getTranslation(hashId)
    }

    suspend fun saveTranslation(entity: TranslationCacheEntity) {
        dao.insertCache(entity)
    }
}
