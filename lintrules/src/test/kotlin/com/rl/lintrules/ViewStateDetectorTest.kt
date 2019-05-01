package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class ViewStateDetectorTest {
    private val activityStub = kotlin("""
        package android.support.v7.app

        class AppCompatActivity

    """).indented()

    private val imageViewStub = kotlin("""
        package android.widget

        class ImageView

    """).indented()

    private val fragmentStub = kotlin("""
        package android.support.v4.app

        class Fragment

    """).indented()

    // Step 1
    @Test
    fun `when an activity keeps a boolean an error given`() {
        lint().files(activityStub, kotlin("""
            package test

            import android.support.v7.app.AppCompatActivity

            class SomeActivity : AppCompatActivity() {

                val testBoolean = false

            }
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }


    // Step 2
    @Test
    fun `when any non-ui class keeps a boolean we expect no error`() {
        lint().files(kotlin("""
            package test

            class SomeClass {

                val testBoolean = false

            }
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    // Step 3
    @Test
    fun `when an activity class keeps a view member we expect no error`() {
        lint().files(imageViewStub, activityStub, kotlin("""
            package test

            import android.support.v7.app.AppCompatActivity
            import android.widget.ImageView

            class SomeActivity : AppCompatActivity() {

                val testView = ImageView()
            }
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}