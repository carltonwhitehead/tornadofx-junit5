package me.carltonwhitehead.tornadofx.junit5

import javafx.application.Platform
import javafx.stage.Stage
import org.assertj.core.api.Assert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.api.FxRobot
import tornadofx.*

@ExtendWith(TornadoFxAppExtension::class)
class TornadoFxAppExtensionTest {

    @App
    private var app: SimpleTestApp? = null

    @Init
    fun init() {
        initCalled = true
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
    }

    @SetupApp
    fun setupApp(): SimpleTestApp {
        setupAppCalled = true
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
        return SimpleTestApp()
    }

    @Start
    fun start(robot: FxRobot) {
        startCalled = true
        Assertions.assertThat(app).isNotNull
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
    }

    @Test
    fun itShouldStartAppAutomatically(stage: Stage) {
        val view = find<SimpleTestView>(app!!.scope)

        Assertions.assertThat(stage.isShowing).isTrue()
        Assertions.assertThat(view.currentStage).isSameAs(stage)
    }

    @Stop
    fun stop() {
        stopCalled = true
        Assertions.assertThat(Platform.isFxApplicationThread()).isTrue()
    }

    companion object {

        private var initCalled = false
        private var setupAppCalled = false
        private var startCalled = false
        private var stopCalled = false

        @JvmStatic
        @AfterAll
        fun afterAll() {
            Assertions.assertThat(initCalled).isTrue()
            Assertions.assertThat(setupAppCalled).isTrue()
            Assertions.assertThat(startCalled).isTrue()
            Assertions.assertThat(stopCalled).isTrue()
        }
    }
}