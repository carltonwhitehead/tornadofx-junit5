package me.carltonwhitehead.tornadofx.junit5

import javafx.beans.property.SimpleIntegerProperty
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assumptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tornadofx.*

@ExtendWith(TornadoFxViewExtension::class)
class TornadoFxViewExtensionDockingTest {

    // Make sure the extension manages to get the @View instance docked and undocked correctly

    @View
    private var view: DockCountingView? = null

    @Init
    fun init(scope: Scope) {
        Assumptions.assumeThat(view).isNull()
        view = find(scope)
    }

    @Test
    fun itShouldHaveDockedForTest() {
        Assertions.assertThat(view!!.docks).isEqualTo(1)
    }

    @Test
    fun itShouldNotHaveUndockedForTest() {
        Assertions.assertThat(view!!.undocks).isEqualTo(0)
    }

    @AfterEach
    fun after() {
        afterView = view
    }

    companion object {
        private var afterView: DockCountingView? = null

        @JvmStatic
        @AfterAll
        fun afterAll() {
            Assertions.assertThat(afterView).isNotNull
            Assertions.assertThat(afterView!!.docks).isEqualTo(1)
            Assertions.assertThat(afterView!!.undocks).isEqualTo(1)
            afterView = null
        }
    }
}

class DockCountingView : tornadofx.View("Dock Counting View") {
    val docksProperty = SimpleIntegerProperty(0)
    var docks by docksProperty

    val undocksProperty = SimpleIntegerProperty(0)
    var undocks by undocksProperty

    override val root = form {
        fieldset {
            field("Docks") {
                text(docksProperty.stringBinding { it?.toString() } )
            }
            field("Undocks") {
                text(undocksProperty.stringBinding { it?.toString() } )
            }
        }
    }

    override fun onDock() {
        super.onDock()
        docks++
    }

    override fun onUndock() {
        super.onUndock()
        undocks++
    }
}