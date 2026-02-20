# SmartAlphaTranslator

A Kotlin Multiplatform (KMP) translation library that provides seamless, automatic text translation for Compose Multiplatform apps. Supports Android (Google ML Kit + OpenAI) and iOS (stubs, extensible).

## Project Structure

```
MySmartAlphaTranslator/
├── smartalphatranslator/    ← 📚 KMP Library (publishable to JitPack)
│   ├── src/commonMain/     ← Shared translation logic, Room DB, Koin DI
│   ├── src/androidMain/    ← ML Kit, OpenAI, Android Room
│   └── src/iosMain/        ← iOS stubs (extensible)
├── composeApp/              ← 📱 Sample App (demonstrates library usage)
│   ├── src/commonMain/     ← Shared Compose UI
│   ├── src/androidMain/    ← Android entry point
│   └── src/iosMain/        ← iOS entry point
└── iosApp/                  ← iOS Xcode project
```

## Installation via JitPack

### Step 1: Add JitPack repository

In your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add the dependency

In your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.prima-rt:MySmartAlphaTranslator:TAG")
}
```
## Usage

### 1. Setup Koin modules (Android)

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                platformDatabaseModule,
                translationModule
            )
        }
    }
}
```

### 2. Provide the translator to your Compose tree

```kotlin
class MainActivity : ComponentActivity() {
    private val translationViewModel: TranslationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val translatorProvider = TranslatorProvider(
            currentLanguage = translationViewModel.language,
            translate = { text -> translationViewModel.translate(text) }
        )

        setContent {
            CompositionLocalProvider(LocalTranslator provides translatorProvider) {
                // Your app content
            }
        }
    }
}
```

### 3. Use SmartAutoText in your composables

```kotlin
@Composable
fun MyScreen() {
    SmartAutoText("Hello, World!") // Automatically translated!
}
```

### 4. Change language programmatically

```kotlin
translationViewModel.setLanguage("Hindi")
translationViewModel.setLanguage("Japanese")
translationViewModel.setLanguage("Spanish")
```

### 5. Switch translation model

```kotlin
translationViewModel.setTranslatorModel(TranslatorModel.GoogleMlKit)
translationViewModel.setTranslatorModel(TranslatorModel.OpenAi)
```

## Supported Languages

Hindi, Bengali, Tamil, Telugu, Marathi, Gujarati, Kannada, Urdu, Japanese, Chinese, Spanish, French, German, Italian, Korean, Arabic, Russian, Portuguese, Thai, Vietnamese, Indonesian, Turkish, Dutch, Polish, Swedish, English

## Building Locally

```bash
./gradlew :smartalphatranslator:assembleDebug   # Build library
./gradlew :composeApp:assembleDebug             # Build sample app
```

## License

Apache 2.0