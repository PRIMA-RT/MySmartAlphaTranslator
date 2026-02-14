/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package app.yulu.design.yulang.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alpharays.mysmartalphatranslator.smartlang.local.dao.TranslationCacheDao
import com.alpharays.mysmartalphatranslator.smartlang.local.entity.TranslationCacheEntity

@Database(entities = [TranslationCacheEntity::class], version = 1, exportSchema = false)
abstract class TranslationAppDatabase : RoomDatabase() {
    abstract fun translationCacheDao(): TranslationCacheDao
}
