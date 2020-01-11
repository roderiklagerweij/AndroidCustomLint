package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.invalidwhen.ISSUE_EXHAUSTIVE_WHEN
import org.junit.Test

class InvalidWhenDetectorTest {

    @Test
    fun `when when result is not used then expect error`() {
        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/main/kotlin/somepackage/ExhaustiveWhen.kt",
            """
            package somepackage

            sealed class State {
                object State1 : HomeViewState()
                object State2 : HomeViewState()
                object State3 : HomeViewState()
            }

            class ExhaustiveWhenTest {
                
                fun test(someState : HomeViewState) {
                    
                    when (it) {
                        is State.State1 -> {
                        }
                        is State.State2 -> {
                        }
                    }
                }

            }
        """
        ).indented())
            .issues(ISSUE_EXHAUSTIVE_WHEN)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when when is combined wiht let then expect clean`() {
        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/main/kotlin/somepackage/ExhaustiveWhen.kt",
            """
            package somepackage

            sealed class State {
                object State1 : HomeViewState()
                object State2 : HomeViewState()
                object State3 : HomeViewState()
            }

            class ExhaustiveWhenTest {
                
                fun test(someState : HomeViewState) {
                    
                    when (it) {
                        is State.State1 -> {
                        }
                        is State.State2 -> {
                        }
                    }.let{}
                }

            }
        """
        ).indented())
            .issues(ISSUE_EXHAUSTIVE_WHEN)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when when result is used then expect error`() {
        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/main/kotlin/somepackage/ExhaustiveWhen.kt",
            """
            package somepackage

            sealed class State {
                object State1 : HomeViewState()
                object State2 : HomeViewState()
                object State3 : HomeViewState()
            }

            class ExhaustiveWhenTest {
                
                fun test(someState : HomeViewState) {
                    
                    val test = when (it) {
                        is State.State1 -> {
                        }
                        is State.State2 -> {
                        }
                    }
                }

            }
        """
        ).indented())
            .issues(ISSUE_EXHAUSTIVE_WHEN)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}