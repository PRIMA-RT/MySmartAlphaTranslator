/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.smartalphatranslator.smartlang.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.alpharays.smartalphatranslator.smartlang.local.TranslationAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val platformDatabaseModule = module {
    single<TranslationAppDatabase> {
        val dbFilePath = NSHomeDirectory() + "/translation_db"
        Room.databaseBuilder<TranslationAppDatabase>(
            name = dbFilePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
