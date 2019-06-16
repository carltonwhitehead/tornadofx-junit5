package me.carltonwhitehead.tornadofx.junit5

import javafx.scene.text.Text
import tornadofx.*
import tornadofx.View

class SimpleTestView : View() {

    init {
        title = SimpleTestView::class.simpleName!!
    }

    override val root = vbox {
        text(titleProperty) {
            id = "title-text"
        }
    }

    val titleText: Text
        get() = root.lookup("#title-text") as Text
}