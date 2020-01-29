package com.rl.lintrules.stats

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.stats.examples.NonFinalRule
import org.junit.Test

class NonFinalRuleTest {
    @Test
    fun `when too many variables are used expect error`() {
        RulesProvider.rulesList = listOf(NonFinalRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            class SomeClass {
                fun method1() {
                    var test1 = "1"
                    var test2 = "2"
                    var test3 = "3"
                    var test4 = "4"
                }
            }
        """
            ).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

}