package com.rl.lintrules.stats

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.rl.lintrules.stats.examples.TooManyLinesRule
import org.jetbrains.uast.UClass
import java.util.*


val ISSUE_CLASS_STATS = Issue.create(
    id = "ClassStats",
    briefDescription = ClassStatsDetector.MESSAGE,
    explanation = ClassStatsDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        ClassStatsDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE))
)

class ClassStatsDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "Lint detector for class stats"
    }

    val rules = listOf(
        TooManyLinesRule()
    )

    override fun getApplicableUastTypes() = listOf(
        UClass::class.java
    )

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitClass(node: UClass) {

                System.out.println(node.text)

                val numberOfLines = node.text.split("\n").size

                val stats = ClassStats(numberOfLines, 0)
                rules.forEach {
                    if (!it.isValid(stats)) {
                        context.report(
                            ISSUE_CLASS_STATS, node,
                            context.getNameLocation(node),
                            it.getMessage())
                    }
                }
            }
        }
    }
}
