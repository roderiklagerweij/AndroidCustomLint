package com.rl.lintrules.stats

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.stats.examples.NullableRule
import org.junit.Test

class NullableRuleTest {

    @Test
    fun `when class has acceptable number of nullables then expect clean`() {
        RulesProvider.rulesList = listOf(NullableRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            class SomeClass {
                fun method1() {
                    val test1 : String?
                    val test2 : String?
                }
            }
        """
            ).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when too many nullables are used expect error`() {
        RulesProvider.rulesList = listOf(NullableRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            class SomeClass {
                fun method1() {
                    val test1 : String?
                    val test2 : String?
                    val test3 : String?
                }
            }
        """
            ).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when too many nullables in dataclass are used expect error`() {
        RulesProvider.rulesList = listOf(NullableRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            data class TestDataClass(
                val test1 : String?,
                val test2 : String?,
                val test3 : String?)
        """
            ).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }
}