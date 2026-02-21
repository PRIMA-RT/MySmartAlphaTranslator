/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.smartalphatranslator.smartlang.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.alpharays.smartalphatranslator.smartlang.local.dao.TranslationCacheDao
import com.alpharays.smartalphatranslator.smartlang.local.entity.TranslationCacheEntity

@Database(entities = [TranslationCacheEntity::class], version = 1, exportSchema = true)
@ConstructedBy(TranslationAppDatabaseConstructor::class)
abstract class TranslationAppDatabase : RoomDatabase() {
    abstract fun translationCacheDao(): TranslationCacheDao
}

// Room KMP requires this for multiplatform database construction
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TranslationAppDatabaseConstructor : RoomDatabaseConstructor<TranslationAppDatabase> {
    override fun initialize(): TranslationAppDatabase
}
