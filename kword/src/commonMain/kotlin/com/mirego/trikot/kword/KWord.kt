package com.mirego.trikot.kword

import kotlin.js.JsName
import com.mirego.trikot.foundation.concurrent.AtomicReference

const val COUNT_ARGUMENT = "count"

interface KWordKey {
    val translationKey: String
}

interface KWordSource {
    @JsName("get")
    fun get(key: String): String
}

class MapKeywordSource(private val source: Map<String, String>) : KWordSource {
    override operator fun get(key: String): String {
        return source[key] ?: key
    }
}

object KWord : I18N {
    private val sourceRef: AtomicReference<KWordSource> =
            AtomicReference(MapKeywordSource(HashMap()))
    private val source: KWordSource
        get() = sourceRef.value
    private val translationArgumentsParser: TranslationArgumentsParser = TranslationArgumentsParser()

    override fun changeLocaleStrings(strings: Map<String, String>) {
        changeLocaleStrings(MapKeywordSource(strings))
    }

    override fun changeLocaleStrings(source: KWordSource) {
        sourceRef.compareAndSet(sourceRef.value, source)
    }

    override operator fun get(kKey: KWordKey): String {
        return source.get(kKey.translationKey)
    }

    override fun t(key: KWordKey): String {
        return this[key]
    }

    override fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): String {
        val keyWithCount = "${key.translationKey}_$count"
        val targetString = source.get(keyWithCount)

        val argumentsWithCount = arguments.toMutableSet()
        argumentsWithCount.add(COUNT_ARGUMENT to count.toString())
        val keyToUse = if (keyWithCount != targetString) keyWithCount else key.translationKey
        return translationArgumentsParser.replaceInTranslation(source.get(keyToUse), argumentsWithCount.toTypedArray())
    }

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>): String {
        return translationArgumentsParser.replaceInTranslation(t(key), arguments)
    }
}
