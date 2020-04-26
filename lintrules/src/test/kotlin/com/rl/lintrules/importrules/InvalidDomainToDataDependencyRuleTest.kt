package com.rl.lintrules.importrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.rl.lintrules.importrules.example.InvalidDomainToDataDependencyRule
import org.junit.Test

class InvalidDomainToDataDependencyRuleTest {
    @Test
    fun `when a domain class imports a class from data package then expect a warning`() {
        RulesProvider.rulesList = listOf(InvalidDomainToDataDependencyRule())

        TestLintTask.lint().files(
            LintDetectorTest.kotlin(
                "src/test/kotlin/somepackage/domain/SomeDomainClass.kt",
                """
            package somepackage.domain

            import somepackage.data.SomeDataClass

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