package me.carltonwhitehead.tornadofx.junit5

import javafx.stage.Stage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.assertions.api.Assertions
import tornadofx.*

@ExtendWith(TornadoFxViewExtension::class)
class TornadoFxViewExtensionAutoStartTest {

    // verify auto-start
    // tests need not use the @Start annotation to show the stage with a basic
    // scene containing the @View

    @View
    private lateinit var view: SimpleTestView

    @Init
    fun init(scope: Scope) {
        view = find(scope)
    }

    @Test
    fun itShouldShowSimpleTestView(stage: Stage) {
        Assertions.assertThat(view.currentStage).isSameAs(stage)
        Assertions.assertThat(stage.isShowing).isTrue()
        Assertions.assertThat(view.isDocked).isTrue()
    }
}
