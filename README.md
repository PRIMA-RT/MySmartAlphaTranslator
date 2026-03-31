# SmartAlphaTranslator

A **Kotlin Multiplatform (KMP)** translation library that provides seamless, automatic text translation for Compose Multiplatform apps. Drop-in replace `Text()` with `SmartAutoText()` and every string auto-translates at runtime.

### ✨ Highlights

- 🔤 **SmartAutoText** — One-line composable that auto-translates any string
- 🧠 **Google ML Kit** — Offline, on-device translation (Android)
- 🤖 **OpenAI** — Cloud-based contextual translation via GPT
- 🌐 **OpenRouter** — Free/flexible cloud translation via OpenRouter API
- 💾 **Smart Caching** — Room-backed persistent cache + in-memory layer (translate once, display instantly)
- 📱 **Multiplatform** — Android & iOS from a single shared codebase

---
## Working Reference

https://github.com/user-attachments/assets/1b0e409d-5b97-423c-b562-493080114f15


---

## Project Structure

```
MySmartAlphaTranslator/
├── smartalphatranslator/         ← 📚 KMP Library (publishable to JitPack)
│   ├── src/commonMain/          ← Shared logic: translators, caching, config, DI
│   │   ├── SmartAutoText.kt     ← Drop-in Text() replacement
│   │   ├── SmartTranslatorConfig.kt ← API key configuration
│   │   ├── OpenAITranslator.kt  ← Ktor-based OpenAI client (multiplatform)
│   │   ├── OpenRouterTranslator.kt ← Ktor-based OpenRouter client (multiplatform)
│   │   ├── TranslationViewModel.kt ← Language, model, caching logic
│   │   ├── local/               ← Room DB, DAO, Entity
│   │   └── di/                  ← Koin modules
│   ├── src/androidMain/         ← Google ML Kit translator, Room DB builder
│   └── src/iosMain/             ← Room DB builder (Darwin), ML Kit stub
├── composeApp/                   ← 📱 Sample/Showcase App
│   ├── src/commonMain/          ← Two-screen showcase (Home + Playground)
│   ├── src/androidMain/         ← Android entry point
│   └── src/iosMain/             ← iOS entry point
└── iosApp/                       ← iOS Xcode project
```

---

## Architecture

```
┌─────────────────────────────────────────────────┐
│                  App Module                      │
│  SmartTranslatorConfig.init(apiKeys)            │
│  startKoin { modules(...) }                     │
│  CompositionLocalProvider(LocalTranslator)      │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│           smartalphatranslator (Library)         │
│                                                  │
│  SmartAutoText() ──► TranslatorProvider          │
│       │                    │                     │
│       ▼                    ▼                     │
│  TranslationViewModel.translate(text)            │
│       │                                          │
│       ├── Memory Cache (HashMap)                 │
│       ├── Room DB Cache (SQLite)                 │
│       └── Translation Engine:                    │
│            ├── GoogleMlKit (Android only)        │
│            ├── OpenAI (Ktor, multiplatform)      │
│            └── OpenRouter (Ktor, multiplatform)  │
└──────────────────────────────────────────────────┘
```

---

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
// KMP (commonMain)
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.github.prima-rt:MySmartAlphaTranslator:TAG")
        }
    }
}
```

---

## Usage

### 1. Configure API Keys

Call `SmartTranslatorConfig.init()` **once** at app startup, before any translation calls.
Pass only the keys for the engines you want to use — leave the rest empty to disable them.

```kotlin
SmartTranslatorConfig.init(
    openAiApiKey = "sk-...",                         // optional
    openRouterApiKey = "sk-or-v1-...",               // optional
    openAiModel = "gpt-4o-mini",                     // optional, default
    openRouterModel = "openai/gpt-oss-120b"          // optional, default
)
```

### 2. Setup Koin Modules

#### Android (`Application.onCreate`)

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        SmartTranslatorConfig.init(
            openRouterApiKey = "sk-or-v1-..."
        )

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

#### iOS (`MainViewController.kt`)

```kotlin
fun initKoin() {
    stopKoin()

    SmartTranslatorConfig.init(
        openRouterApiKey = "sk-or-v1-..."
    )

    startKoin {
        modules(
            platformDatabaseModule,
            translationModule
        )
    }
}
```

### 3. Provide the Translator to Your Compose Tree

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
                // Your app content here
            }
        }
    }
}
```

### 4. Use SmartAutoText in Your Composables

```kotlin
@Composable
fun MyScreen() {
    // Automatically translated to the selected language!
    SmartAutoText("Hello, World!")

    // With full styling support — same API as Text()
    SmartAutoText(
        text = "Welcome to the app",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    // Opt out of translation for specific text
    SmartAutoText(
        text = "Brand Name™",
        autoTranslate = false
    )
}
```

### 5. Change Language Programmatically

```kotlin
translationViewModel.setLanguage("Hindi")
translationViewModel.setLanguage("Japanese")
translationViewModel.setLanguage("Spanish")
translationViewModel.setLanguage("French")
```

### 6. Switch Translation Engine

```kotlin
// Offline — Google ML Kit (Android only)
translationViewModel.setTranslatorModel(TranslatorModel.GoogleMlKit)

// Cloud — OpenAI GPT
translationViewModel.setTranslatorModel(TranslatorModel.OpenAi)

// Cloud — OpenRouter (free models available)
translationViewModel.setTranslatorModel(TranslatorModel.OpenRouter)
```

---

## Translation Engines

| Engine | Platform | Type | Notes |
|--------|----------|------|-------|
| **Google ML Kit** | Android only | Offline | On-device, no API key needed, downloads language models |
| **OpenAI** | Android + iOS | Cloud | Requires API key, uses GPT models, best contextual quality |
| **OpenRouter** | Android + iOS | Cloud | Requires API key, supports free models, OpenAI-compatible API |

> **iOS Note:** ML Kit is unavailable on iOS — the library returns the original text gracefully. Cloud engines (OpenAI & OpenRouter) work fully on both platforms via Ktor.

---

## Caching Strategy

Translations are cached at two levels for performance:

1. **In-memory cache** (`HashMap`) — Instant lookup, cleared on process death
2. **Room/SQLite persistent cache** — Survives app restarts, shared across sessions

Cache key = `hash(language + model + sourceText)` using FNV-1a 64-bit hash.

On a cache hit, no network call is made — the translated text is returned immediately.

---

## Supported Languages

Hindi, Bengali, Tamil, Telugu, Marathi, Gujarati, Kannada, Urdu, Japanese, Chinese, Spanish, French, German, Italian, Korean, Arabic, Russian, Portuguese, Thai, Vietnamese, Indonesian, Turkish, Dutch, Polish, Swedish, English

---

## Sample App

The included `composeApp` module is a two-screen showcase:

- **Home Screen** — Animated landing page showcasing library features
- **Translation Playground** — Interactive screen with:
  - Language selector dropdown (15+ languages)
  - Engine picker (ML Kit / OpenAI / OpenRouter)
  - Text input with **2-second debounce** (translates after you stop typing)
  - Live translation output via `SmartAutoText`
  - Quick sample phrases to tap and translate
  - Live demo section with auto-translating UI strings

---

## Building Locally

```bash
# Build the library
./gradlew :smartalphatranslator:assembleDebug

# Build the sample app (Android)
./gradlew :composeApp:assembleDebug

# Run the sample app
./gradlew :composeApp:installDebug
```

---

## License

Apache 2.0
