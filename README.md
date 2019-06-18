# tornadofx-junit5
JUnit 5 extensions for TornadoFX

These extensions expand on [TestFX](https://github.com/TestFX/TestFX)'s JUnit 5 `ApplicationExtension` with features aimed at simplifying the testing of TornadoFX apps.

## TornadoFxViewExtension

- Convenient callbacks to set up and tear down the annotated `@View` instance under test.
    - `@Init`: Make any preparations and then `find()` the view.
    - `@Start`: Optionally customize the `Stage` for your view. By default the extension will create a `Scene` with your view's root and show the `Stage`.
    - `@Stop`: Optionally clean up after your test instance.
- All callbacks occur on the FX application thread which reduces boilerplate. 
- All test cycles receive a new `Scope`.
- Inject parameters into your test method:
    - `Stage`
    - `FxRobot`
    - `Scope`
    - `App`

Basic example:

```kotlin
@ExtendWidth(TornadoFxViewExtension::class)
class SomeViewTest {
    @View
    lateinit var view: SomeView
    
    @Init
    fun init(scope: Scope) {
        view = find(scope)
    }
    
    @Test
    fun itShouldShowSomeViewOnStage(stage: Stage) {
        Assertions.assertThat(view.currentStage).isSameAs(stage)
        Assertions.assertThat(stage.isShowing).isTrue()
        Assertions.assertThat(view.isDocked).isTrue()
    }
}
```

## TornadoFxAppExtension

- Convenient callbacks to set up and tear down the annotated `@App` instance under test.
    - `@Init`: Make any preparations prior to creating your app instance.
    - `@SetupApp`: Instantiate and return your `App` instance.
    - `@Start`: Optionally use a `FxRobot` to put your app into a certain state prior to each `@Test` function.
    - `@Stop`: Optionally clean up after your test instance.
- All callbacks occur on the FX application thread which reduces boilerplate. 
- Inject parameters into your test method:
    - `Stage`
    - `FxRobot`

Basic example:

```kotlin
@ExtendWith(TornadoFxAppExtension::class)
class SomeAppTest {

    @App
    lateinit var app: SomeApp

    @SetupApp
    fun setupApp() = SomeApp()

    @Test
    fun itShouldStartAppAutomatically(stage: Stage) {
        val view = find<SomeView>(app.scope)
        Assertions.assertThat(stage.isShowing).isTrue()
        Assertions.assertThat(view.currentStage).isSameAs(stage)
    }

}
```