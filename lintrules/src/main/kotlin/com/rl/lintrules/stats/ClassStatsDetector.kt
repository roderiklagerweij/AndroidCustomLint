package com.rl.lintrules.stats

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.rl.lintrules.stats.examples.DoubleBangRule
import com.rl.lintrules.stats.examples.TooManyLinesRule
import com.rl.lintrules.stats.examples.NonFinalRule
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.visitor.AbstractUastVisitor
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
        TooManyLinesRule(),
        DoubleBangRule(),
        NonFinalRule()
    )

    override fun getApplicableUastTypes() = listOf(
        UClass::class.java
    )

    override fun afterCheckFile(context: Context) {
        super.afterCheckFile(context)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {


            override fun visitClass(node: UClass) {

                var nonFinalCounter = 0
                node.accept(object : AbstractUastVisitor() {

                    override fun visitDeclaration(node: UDeclaration): Boolean {
                        if (!node.isFinal) {
                            nonFinalCounter++
                        }
                        return super.visitDeclaration(node)
                    }

                })
                val numberOfLines = node.text.split("\n").size
                val numberOfDoubleBangs = "!!".count { node.text.contains(it) }

                val stats = ClassStats(
                    numberOfLines,
                    0,
                    numberOfDoubleBangs,
                    nonFinalCounter)


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
