package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class UserInterfaceInheritanceDetectorTest {
    private val activityStub = kotlin("""
        package android.support.v7.app

        class AppCompatActivity

    """).indented()

    private val fragmentStub = kotlin("""
        package android.support.v4.app

        class Fragment

    """).indented()

    // Step 1
    @Test
    fun `when the parent of an activity is not the support activity an error is given`() {
        lint().files(activityStub, kotlin("""
            package test
            import android.support.v7.app.AppCompatActivity

            class SomeActivity : BaseActivity()

            class BaseActivity : AppCompatActivity()
        """).indented())
            .issues(ISSUE_USER_INTERFACE_INHERITANCE)
            .run()
            .expectErrorCount(1)
    }


    // Step 2
    @Test
    fun `when a class is not an activity no error is given`() {
        lint().files(kotlin("""
            package test

            class SomeClass
        """).indented())
                .issues(ISSUE_USER_INTERFACE_INHERITANCE)
                .run()
                .expectClean()
    }

    // Step 3
    @Test
    fun `when the parent of an activity is the support activity no error is given`() {
        lint().files(activityStub, kotlin("""
            package test
            import android.support.v7.app.AppCompatActivity

            class SomeActivity : AppCompatActivity() {

            }
        """).indented())
                .issues(ISSUE_USER_INTERFACE_INHERITANCE)
                .run()
                .expectClean()
    }


    // Step 4
    @Test
    fun `when the parent of a fragment is not the support fragment an error is given`() {
        lint().files(fragmentStub, kotlin("""
            package test
            import android.support.v4.app.Fragment

            class SomeFragment : BaseFragment()

            class BaseFragment : Fragment()
        """).indented())
                .issues(ISSUE_USER_INTERFACE_INHERITANCE)
                .run()
                .expectErrorCount(1)
    }
}