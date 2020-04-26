package com.rl.lintrules.importrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.gradle
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.importrules.example.InvalidEspressoImportRule
import org.junit.Test

class InvalidEspressoImportRuleTest {

    @Test
    fun `when a test class imports from an espresso package then expect a warning`() {
        RulesProvider.rulesList = listOf(InvalidEspressoImportRule())

        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/test/kotlin/somepackage/SomeFragmentTest.kt",
            """
            package somepackage

            import androidx.test.espresso.Espresso.onView

            class SomeFragmentTest {

            }
        """
        ).indented(),
            gradle(
                """
                android {
                    lintOptions {
                        checkTestSources true
                    }
                }
                """
            ).indented())
            .issues(ISSUE_IMPORT_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when a test class does not import from an espresso package then expect clean`() {
        RulesProvider.rulesList = listOf(InvalidEspressoImportRule())

        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/test/kotlin/somepackage/SomeFragmentTest.kt",
            """
            package somepackage

            class SomeFragmentTest {

            }
        """
        ).indented(),
            gradle(
                """
                android {
                    lintOptions {
                        checkTestSources true
                    }
                }
                """
            ).indented())
            .issues(ISSUE_IMPORT_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }


    @Test
    fun `when a journey class imports from an espresso package then expect a warning`() {
        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/test/kotlin/somepackage/SomeJourney.kt",
            """
            package somepackage

            import androidx.test.espresso.Espresso.onView

            class SomeJourney {

            }
        """
        ).indented(),
            gradle(
                """
                android {
                    lintOptions {
                        checkTestSources true
                    }
                }
                """
            ).indented())
            .issues(ISSUE_IMPORT_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when a robot class imports from an espresso package then expect clean`() {
        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/test/kotlin/somepackage/SomeFragmentTestRobot.kt",
            """
            package somepackage

            import androidx.test.espresso.Espresso.onView

            class SomeFragmentTestRobot {

            }
        """
        ).indented(),
            gradle(
                """
                android {
                    lintOptions {
                        checkTestSources true
                    }
                }
                """
            ).indented())
            .issues(ISSUE_IMPORT_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}