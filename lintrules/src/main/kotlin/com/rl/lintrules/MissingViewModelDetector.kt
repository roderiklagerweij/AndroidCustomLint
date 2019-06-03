package com.rl.lintrules

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import java.util.*


val ISSUE_MISSING_VIEWMODEL_DETECTOR = Issue.create(
    id = "MissingViewModel",
    briefDescription = MissingViewModelDetector.MESSAGE,
    explanation = MissingViewModelDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        MissingViewModelDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE)))


class MissingViewModelDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "ViewModel is missing for this fragment (or has incorrect name or is in wrong package), please create one"
        private val UI_CLASSES =  listOf(
            "android.support.v4.app.Fragment",
            "androidx.fragment.app.Fragment")
    }

    override fun applicableSuperClasses() = UI_CLASSES

    override fun visitClass(context: JavaContext, declaration: UClass) {

        if (context.evaluator.getQualifiedName(declaration) in UI_CLASSES) return

        declaration.qualifiedName?.let { qualifiedName ->
            declaration.name?.let { name ->
                val packageName = qualifiedName.dropLast(name.length + 1)
                val viewModelName = name.dropLast("Fragment".length) + "ViewModel"
                if (context.evaluator.findClass("${packageName}.${viewModelName}") == null) {
                    context.report(
                        ISSUE_MISSING_VIEWMODEL_DETECTOR, declaration,
                        context.getNameLocation(declaration),
                        MissingViewModelDetector.MESSAGE
                    )
                }
            }
        }
    }
}
