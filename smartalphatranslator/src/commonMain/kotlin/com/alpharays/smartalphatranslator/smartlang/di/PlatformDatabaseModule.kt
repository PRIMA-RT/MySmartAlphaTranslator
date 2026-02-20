/**
 * Created by Sumit Kumar
 * Date: 15/11/25
 */

package com.alpharays.smartalphatranslator.smartlang.di

/**
 * Platform-specific module to provide the Room database instance.
 * Each platform (Android, iOS) must supply this.
 */
expect val platformDatabaseModule: org.koin.core.module.Module
