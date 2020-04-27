package com.mirego.trikot.kword.android.ktx

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWord
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseMap
import java.io.IOException

object AndroidKWord {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        serializeSpecialFloatingPointValues = true
    }

    fun setCurrentLanguageCode(code: String) {
        setCurrentLanguageCodes(KWord, code)
    }

    fun setCurrentLanguageCode(i18N: I18N, code: String) {
        setCurrentLanguageCodes(i18N, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        val map = mutableMapOf<String, String>()
        val variant = mutableListOf<String>()
        codes.forEach {
            variant.add(it)
            val variantPath = variant.joinToString(".")
            try {
                val fileContent =
                    KWord::class.java.getResource("/translations/translation.$variantPath.json")!!
                        .readText()
                map.putAll(json.parseMap<String, String>(fileContent))
            } catch (ioException: IOException) {
            }
        }
        i18N.changeLocaleStrings(map)
    }
}
