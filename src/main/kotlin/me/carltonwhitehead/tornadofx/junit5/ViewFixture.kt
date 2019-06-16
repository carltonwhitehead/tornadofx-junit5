package me.carltonwhitehead.tornadofx.junit5

import tornadofx.View
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1

class ViewFixture(
        val init: KFunction<*>?,
        val start: KFunction<*>?,
        val stop: KFunction<*>?,
        val view: KProperty1<Any, View>?
)