package com.rl.lintrules.importrules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UImportStatement
import org.jetbrains.uast.getContainingUFile
import java.util.*

val ISSUE_IMPORT_DETECTOR = Issue.create(
    id = "IncorrectImportDetector",
    briefDescription = ImportDetector.MESSAGE,
    explanation = ImportDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 9,
    severity = Severity.ERROR,
    implementation = Implementation(
        ImportDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE))
    )

class ImportDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "Lint detector for detecting invalid imports"
    }

    val rules = RulesProvider.rulesList

    override fun getApplicableUastTypes() = listOf(UImportStatement::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                node.importReference?.let { import ->
                    rules.forEach { rule ->
                        val visitingPackageName = import.getContainingUFile()?.packageName
                        val visitingClassName = context.file.nameWithoutExtension
                        val importedClass = import.asRenderString()
                        visitingPackageName?.let {
                            if (!rule.isAllowedImport(
                                    visitingPackageName,
                                    visitingClassName,
                                    importedClass)) {

                                context.report(
                                    ISSUE_IMPORT_DETECTOR, node,
                                    context.getLocation(import),
                                    rule.getMessage())
                            }
                        }
                    }
                }
            }
        }
    }
}

fun test(node: UImportStatement) {

}
