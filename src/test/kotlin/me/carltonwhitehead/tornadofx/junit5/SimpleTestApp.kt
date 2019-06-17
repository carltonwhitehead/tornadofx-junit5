package me.carltonwhitehead.tornadofx.junit5

import tornadofx.*

class SimpleTestApp : tornadofx.App(SimpleTestView::class) {
    override var scope = Scope()
}