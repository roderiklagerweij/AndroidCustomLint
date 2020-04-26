package com.rl.lintrules.importrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.gradle
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.importrules.example.InvalidFeatureImportRule
import org.junit.Test

class InvalidFeatureImportRuleTest {

    @Test
    fun `when a file from a feature package imports a class from another feature package then expect a warning`() {
        RulesProvider.rulesList = listOf(InvalidFeatureImportRule())

        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/test/kotlin/features/featurex/SomeClassX.kt",
            """
            package com.example.features.x

            import com.example.features.y.SomeOtherClass

            class SomeClassX {

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
    fun `when a file from a feature package imports a class from the same feature package then expect clean`() {
        RulesProvider.rulesList = listOf(InvalidFeatureImportRule())

        TestLintTask.lint().files(LintDetectorTest.kotlin(
            "src/test/kotlin/features/featurex/SomeClassX.kt",
            """
            package com.example.features.x

            import com.example.features.x.SomeOtherClass

            class SomeClassX {

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