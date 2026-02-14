/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.mysmartalphatranslator.smartlang.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.alpharays.mysmartalphatranslator.smartlang.local.TranslationAppDatabase
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    single<TranslationAppDatabase> {
        val context = get<android.content.Context>()
        Room.databaseBuilder(
            context,
            TranslationAppDatabase::class.java,
            "translation_db"
        ).build()
    }
}
