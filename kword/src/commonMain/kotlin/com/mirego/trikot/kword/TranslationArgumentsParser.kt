package com.mirego.trikot.kword

internal class TranslationArgumentsParser {
    fun replaceInTranslation(translation: String, arguments: Map<String, String>): String {
        var parsedTranslation: String = translation

        for (argument in arguments) {
            parsedTranslation = parsedTranslation.replace("{{${argument.key}}}", argument.value)
        }

        return parsedTranslation
    }
}
