package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class MissingRobotDetectorTest {
    private val fragmentStub = kotlin("""
        package android.support.v4.app

        class Fragment

    """).indented()

    @Test
    fun `when a fragment has no robot an error is given`() {
        lint().files(fragmentStub, kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() {

            }
        """).indented())
            .issues(ISSUE_MISSING_ROBOT_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when a fragment has a robot no error is given`() {
        lint().files(fragmentStub, kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() {

            }

            class SomeRobot {

            }
        """).indented())
            .issues(ISSUE_MISSING_ROBOT_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}