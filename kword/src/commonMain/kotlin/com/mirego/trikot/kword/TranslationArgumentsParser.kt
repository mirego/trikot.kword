package com.mirego.trikot.kword

internal class TranslationArgumentsParser {
    fun replaceInTranslation(translation: String, arguments: Map<String, String>) =
        translation.replace(Regex("\\{\\{([^}]+)}}")) {
            arguments[it.groupValues[1]] ?: it.value
        }
}
