package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
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

    private val viewModelStub = kotlin("""
        package androidx.lifecycle

        class ViewModel

    """).indented()

    private val fragmentStub = kotlin("""
        package android.support.v4.app

        class Fragment

    """).indented()

    // Step 1
    @Test
    fun `when an activity keeps a boolean an error is given`() {
        lint().files(activityStub, kotlin("""
            package test

            import android.support.v7.app.AppCompatActivity

            class SomeActivity : AppCompatActivity() {

                var testBoolean = false

            }
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }


    // Step 2
    @Test
    fun `when a non-ui class keeps a boolean we expect no error`() {
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

    @Test
    fun `when an activity class keeps a viewmodel member we expect no error`() {
        lint().files(viewModelStub, activityStub, kotlin("""
            package test

            import android.support.v7.app.AppCompatActivity
            import androidx.lifecycle.ViewModel

            class SomeActivity : AppCompatActivity() {

                val testViewModel = SomeViewModel()
            }

            class SomeViewModel : ViewModel()
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when an activity class keeps an adapter we expect no error`() {
        lint().files(activityStub, kotlin("""
            package test

            import android.support.v7.app.AppCompatActivity

            class SomeActivity : AppCompatActivity() {

                val testAdapter = SomeAdapter()
            }

            class SomeAdapter
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when an activity class keeps a static member we expect no error`() {
        lint().files(activityStub, java("""
            package test;

            import android.support.v7.app.AppCompatActivity;

            class SomeActivity extends AppCompatActivity {

                private static String TEST = "123";
            }
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when an activity class keeps a companion object we expect no error`() {
        lint().files(activityStub, kotlin("""
            package test

            import android.support.v7.app.AppCompatActivity

            class SomeActivity : AppCompatActivity() {
                companion object {
                    const val TEST = "Test"

                }

            }

        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when an activity class keeps a final member we expect no error`() {
        lint().files(activityStub, java("""
            package test;

            import android.support.v7.app.AppCompatActivity;

            class SomeActivity extends AppCompatActivity {

                private final String test = "123";
            }
        """).indented())
            .issues(ISSUE_VIEW_STATE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }



}