/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.smartalphatranslator.smartlang.di

import com.alpharays.smartalphatranslator.smartlang.TranslationViewModel
import com.alpharays.smartalphatranslator.smartlang.data.TranslationRepository
import com.alpharays.smartalphatranslator.smartlang.local.TranslationAppDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Common Koin module for translation layer.
 * Platform-specific database builder provided via [platformDatabaseModule].
 */
val translationModule = module {
    // DAO from the database
    single { get<TranslationAppDatabase>().translationCacheDao() }

    // Repository
    single { TranslationRepository(get()) }

    // ViewModel
    viewModel { TranslationViewModel(get()) }
}
