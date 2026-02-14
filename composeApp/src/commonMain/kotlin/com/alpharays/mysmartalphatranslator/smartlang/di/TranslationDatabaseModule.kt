/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.mysmartalphatranslator.smartlang.di

import android.content.Context
import androidx.room.Room
import app.yulu.design.yulang.local.TranslationAppDatabase
import com.alpharays.mysmartalphatranslator.smartlang.local.dao.TranslationCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslationDatabaseModule {

    @Provides
    @Singleton
    fun provideTranslationAppDatabase(context: Context): TranslationAppDatabase {
        return Room.databaseBuilder(
            context,
            TranslationAppDatabase::class.java,
            "translation_db"
        ).build()
    }

    @Provides
    fun provideTranslationCacheDao(db: TranslationAppDatabase): TranslationCacheDao {
        return db.translationCacheDao()
    }
}
