package com.mirego.trikot.kword

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KWordTests {
    val simpleKey = object : KWordKey { override val translationKey = "simpleKey" }
    val argumentKey = object : KWordKey { override val translationKey = "argumentKey" }
    val defaultKey = object : KWordKey { override val translationKey = "zero_one_many" }
    val zeroKey = object : KWordKey { override val translationKey = "zero_one_many_0" }
    val threeKey = object : KWordKey { override val translationKey = "zero_one_many_3" }
    val oneKey = object : KWordKey { override val translationKey = "zero_one_many_1" }

    val expectedArgument = "ARGS!"

    val strings = mapOf(
        simpleKey.translationKey to simpleKey.translationKey,
        argumentKey.translationKey to "${argumentKey.translationKey} {{argument}}",
        zeroKey.translationKey to "${zeroKey.translationKey} {{argument}} {{count}}",
        oneKey.translationKey to "${oneKey.translationKey} {{argument}} {{count}}",
        threeKey.translationKey to "${threeKey.translationKey} {{argument}} {{count}}",
        defaultKey.translationKey to "${defaultKey.translationKey} {{argument}} {{count}}"
    )

    @BeforeTest
    fun setup() {
        KWord.changeLocaleStrings(strings)
    }

    @Test
    fun noArgumentTests() {
        assertEquals(simpleKey.translationKey, KWord.t(simpleKey))
    }

    @Test
    fun argumentTests() {
        val translated = KWord.t(argumentKey, "argument" to expectedArgument)
        assertEquals("argumentKey $expectedArgument", translated)
    }

    @Test
    fun zeroOneManyTest() {
        val results = listOf(0, 1, 2, 3).map { KWord.t(defaultKey, it, "argument" to expectedArgument) }

        assertEquals("${zeroKey.translationKey} $expectedArgument 0", results[0])
        assertEquals("${oneKey.translationKey} $expectedArgument 1", results[1])
        assertEquals("${defaultKey.translationKey} $expectedArgument 2", results[2])
        assertEquals("${threeKey.translationKey} $expectedArgument 3", results[3])
    }

}
