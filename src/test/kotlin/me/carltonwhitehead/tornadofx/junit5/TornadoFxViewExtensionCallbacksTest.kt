package me.carltonwhitehead.tornadofx.junit5

import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.api.FxRobot
import tornadofx.*

@ExtendWith(TornadoFxViewExtension::class)
class TornadoFxViewExtensionCallbacksTest {

    // verify the view extension calls all needed callbacks

    @View
    private lateinit var view: SimpleTestView

    @Init
    fun init(scope: Scope) {
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
        didInit = true
        view = find(scope)
    }

    @Start
    fun start(stage: Stage) {
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
        didStart = true
        stage.scene = Scene(view.root)
        stage.show()
    }

    @Stop
    fun stop() {
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
        didStop = true
    }

    @Test
    fun itShouldDisplayView() {
        Assertions.assertThat(view.titleText.text).isNotBlank()
    }

    companion object {

        private var didInit = false
        private var didStart = false
        private var didStop = false

        @JvmStatic
        @AfterAll
        fun afterAll() {
            Assertions.assertThat(didInit).isTrue()
            Assertions.assertThat(didStart).isTrue()
            Assertions.assertThat(didStop).isTrue()
        }
    }
}

