package com.mirego.trikot.kword

import com.mirego.trikot.foundation.CommonJSExport
import kotlin.js.JsName

@CommonJSExport
interface KWordKey {
    val translationKey: String
}

@CommonJSExport
interface KWordSource {
    fun get(key: String): String

    val strings: Map<String, String>
}

@CommonJSExport
class MapKeywordSource(override val strings: Map<String, String>) : KWordSource {
    override operator fun get(key: String): String {
        return strings[key] ?: key
    }
}

object KWord : DefaultI18N()
