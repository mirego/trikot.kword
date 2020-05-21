package com.mirego.trikot.kword

import com.mirego.trikot.foundation.CommonJSExport
import kotlin.js.JsName

@CommonJSExport
interface I18N {
    operator fun get(key: KWordKey): String

    @JsName("changeLocaleStrings")
    fun changeLocaleStrings(strings: Map<String, String>)

    fun changeLocaleStrings(source: KWordSource)

    fun t(key: KWordKey): String

    fun t(key: KWordKey, count: Int, vararg arguments: String): String

    fun t(key: KWordKey, vararg arguments: Pair<String, String>): String
}
