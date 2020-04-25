package com.rl.lintrules.importrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.importrules.example.InvalidDomainToPresentationDependencyRule
import org.junit.Test

class InvalidDomainToPresentationDependencyRuleTest {
    @Test
    fun `when a domain class imports a class from ui package then expect a warning`() {
        RulesProvider.rulesList = listOf(InvalidDomainToPresentationDependencyRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                "src/test/kotlin/somepackage/domain/SomeDomainClass.kt",
                """
            package somepackage.domain

            import somepackage.ui.SomeUiClass

            class SomeDomainClass {

            }
        """
            ).indented(),
            LintDetectorTest.gradle(
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
            .expectWarningCount(1)
    }

}