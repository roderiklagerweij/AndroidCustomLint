package com.rl.lintrules.stats

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.stats.examples.DoubleBangRule
import org.junit.Test

class DoubleBangRuleTest {
    @Test
    fun `when for a class has !! then expect error`() {
        RulesProvider.rulesList = listOf(DoubleBangRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            class SomeClass {
                fun method1() {
                    var test : String? = null
                    System.out.println(test!!)
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