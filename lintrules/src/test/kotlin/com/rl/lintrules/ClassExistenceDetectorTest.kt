package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.rl.lintrules.classexistence.ISSUE_CLASS_EXISTENCE
import org.junit.Test

class ClassExistenceDetectorTest {

    @Test
    fun `when for a fragment there is no viewmodel then expect error`() {
        lint().files(kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() {

            }
        """).indented())
            .issues(ISSUE_CLASS_EXISTENCE)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when for a fragment there is a viewmodel then expect clean`() {
        lint().files(kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() {

            }
            
            class SomeViewModel {
            
            }

        """).indented())
            .issues(ISSUE_CLASS_EXISTENCE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}