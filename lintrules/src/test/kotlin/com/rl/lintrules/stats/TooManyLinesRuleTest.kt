package com.rl.lintrules.stats

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.stats.examples.TooManyLinesRule
import org.junit.Test

class TooManyLinesRuleTest {
    @Test
    fun `when for a class has 15 or less lines then expect clean`() {
        RulesProvider.rulesList = listOf(TooManyLinesRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            class SomeClass {
                fun method1() {

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
    fun `when for a class has more then 15 lines then expect error`() {
        RulesProvider.rulesList = listOf(TooManyLinesRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                """
            package test

            class SomeClass {
                fun method1() {
                
                
               
                
                
                
                
                
                }
                            
                fun method2() {
                
                
                
                
                
                
                
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