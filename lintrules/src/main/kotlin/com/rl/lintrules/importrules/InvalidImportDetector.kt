package com.rl.lintrules.importrules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.rl.lintrules.importrules.example.InvalidEspressoImportRule
import org.jetbrains.uast.UImportStatement
import java.util.*

val ISSUE_IMPORT_DETECTOR = Issue.create(
    id = "IncorrectImportDetector",
    briefDescription = ImportDetector.MESSAGE,
    explanation = ImportDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.WARNING,
    implementation = Implementation(
        ImportDetector::class.java,
        EnumSet.of(Scope.TEST_SOURCES, Scope.JAVA_FILE),
        EnumSet.of(Scope.TEST_SOURCES, Scope.JAVA_FILE))
    )

class ImportDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "Lint detector for detecting invalid imports"
    }

    val rules = listOf(InvalidEspressoImportRule())

    override fun getApplicableUastTypes() = listOf(UImportStatement::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                node.importReference?.let { import ->
                    rules.forEach {
                        if (!it.isAllowedImport(context.file.nameWithoutExtension, context.isTestSource, import.asRenderString())) {
                            context.report(
                                ISSUE_IMPORT_DETECTOR, node,
                                context.getLocation(import),
                                it.getMessage())
                        }
                    }
                }
            }
        }
    }
}
