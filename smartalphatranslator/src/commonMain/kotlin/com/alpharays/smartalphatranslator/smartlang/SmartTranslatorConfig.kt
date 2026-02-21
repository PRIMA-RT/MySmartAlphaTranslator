package com.alpharays.smartalphatranslator.smartlang

/**
 * Configuration for SmartAlphaTranslator library.
 *
 * The consuming app must call [SmartTranslatorConfig.init] before using
 * cloud translation features (OpenAI / OpenRouter).
 *
 * Example (Android Application.onCreate):
 * ```
 * SmartTranslatorConfig.init(
 *     openAiApiKey = "sk-...",
 *     openRouterApiKey = "sk-or-...",
 *     openAiModel = "gpt-4o-mini",           // optional, defaults to gpt-4o-mini
 *     openRouterModel = "openai/gpt-oss-120b" // optional, defaults to openai/gpt-oss-120b
 * )
 * ```
 */
object SmartTranslatorConfig {

    /** OpenAI API key. Empty = OpenAI translations will return original text. */
    var openAiApiKey: String = ""
        private set

    /** OpenRouter API key. Empty = OpenRouter translations will return original text. */
    var openRouterApiKey: String = ""
        private set

    /** OpenAI model name (default: gpt-4o-mini). */
    var openAiModel: String = "gpt-4o-mini"
        private set

    /** OpenRouter model name (default: openai/gpt-oss-120b). */
    var openRouterModel: String = "openai/gpt-oss-120b"
        private set

    /**
     * Initialize the translator configuration.
     * Must be called once during app startup before any translation calls.
     *
     * @param openAiApiKey       Your OpenAI API key (pass empty string to disable OpenAI)
     * @param openRouterApiKey   Your OpenRouter API key (pass empty string to disable OpenRouter)
     * @param openAiModel        OpenAI model to use (default: "gpt-4o-mini")
     * @param openRouterModel    OpenRouter model to use (default: "openai/gpt-oss-120b")
     */
    fun init(
        openAiApiKey: String = "",
        openRouterApiKey: String = "",
        openAiModel: String = "gpt-4o-mini",
        openRouterModel: String = "openai/gpt-oss-120b"
    ) {
        this.openAiApiKey = openAiApiKey
        this.openRouterApiKey = openRouterApiKey
        this.openAiModel = openAiModel
        this.openRouterModel = openRouterModel
        println("AlphaLangLogging SmartTranslatorConfig initialized — OpenAI: ${if (openAiApiKey.isNotEmpty()) "✓" else "✗"}, OpenRouter: ${if (openRouterApiKey.isNotEmpty()) "✓" else "✗"}")
    }
}
