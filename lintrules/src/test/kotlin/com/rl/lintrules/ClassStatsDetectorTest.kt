package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.rl.lintrules.classexistence.ISSUE_CLASS_EXISTENCE
import com.rl.lintrules.stats.ISSUE_CLASS_STATS
import org.junit.Test

class ClassStatsDetectorTest {

    @Test
    fun `when for a class has 15 or less lines then expect clean`() {
        lint().files(kotlin("""
            package test

            class SomeClass {
                fun method1() {

                }
            }
        """).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }


    @Test
    fun `when for a class has more then 15 lines then expect error`() {
        lint().files(kotlin("""
            package test

            class SomeClass {
                fun method1() {
                
                
               
                
                
                
                
                
                }
                            
                fun method2() {
                
                
                
                
                
                
                
                }
            }
        """).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when for a class has !! then expect error`() {
        lint().files(kotlin("""
            package test

            class SomeClass {
                fun method1() {
                    var test : String? = null
                    System.out.println(test!!)
                }
            }
        """).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when too many variables are used expect error`() {
        lint().files(kotlin("""
            package test

            class SomeClass {
                fun method1() {
                    var test1 = "1"
                    var test2 = "2"
                    var test3 = "3"
                    var test4 = "4"
                }
            }
        """).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when too many nullables are used expect error`() {
        lint().files(kotlin("""
            package test

            class SomeClass {
                fun method1() {
                    val test1 : String? = null
                    val test2 : String?
                    val test3 : String? = null
                    val test4 : String?
                    val test5 : String? = null
                    val test6 : String = "Test"
                }
            }
        """).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when too many nullables in dataclass are used expect error`() {
        lint().files(kotlin("""
            package test

            data class TestDataClass(
                var test1 : String?,
                var test2 : String?,
                var test3 : String?)
        """).indented())
            .issues(ISSUE_CLASS_STATS)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }



}