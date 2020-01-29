package com.rl.lintrules.stats

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UUnaryExpression
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

    val rules = RulesProvider.rulesList

    override fun getApplicableUastTypes() = listOf(
        UClass::class.java
    )

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {


            override fun visitClass(node: UClass) {
                System.out.println("Visit class")
                var nonFinalCounter = 0
                var nullableTypeCounter = 0
                node.accept(object : AbstractUastVisitor() {

                    override fun visitUnaryExpression(node: UUnaryExpression): Boolean {
                        System.out.println("****")
                        System.out.println(node.asRenderString())
                        return super.visitUnaryExpression(node)
                    }

                    override fun visitDeclaration(node: UDeclaration): Boolean {
                        System.out.println("Visit decleration: " + node.text + " " + node)

                        // Nullable types
                        node.sourcePsi?.children?.filterIsInstance<KtTypeReference>()?.map {
                            if (it.typeElement is KtNullableType) {
                                System.out.println("Yes: " + it.text)
                                nullableTypeCounter++
                            }
                        }

                        // Non final types
                        if (node.sourcePsi is KtProperty) {
                            if ((node.sourcePsi as KtProperty).valOrVarKeyword.text == "var") {
                                nonFinalCounter++
                            }

                        }
                        return super.visitDeclaration(node)
                    }

                })
                val numberOfLines = node.text.split("\n").size
                val numberOfDoubleBangs = "!!".count { node.text.contains(it) }

                val stats = ClassStats(
                    numberOfLines,
                    numberOfDoubleBangs,
                    nonFinalCounter,
                    nullableTypeCounter)

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
