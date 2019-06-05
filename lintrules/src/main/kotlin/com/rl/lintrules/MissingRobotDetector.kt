package com.rl.lintrules

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import java.util.*

val ISSUE_MISSING_ROBOT_DETECTOR = Issue.create(
    id = "MissingRobot",
    briefDescription = MissingRobotDetector.MESSAGE,
    explanation = MissingRobotDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        MissingRobotDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE)))


class MissingRobotDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "Robot is missing for this fragment (or has incorrect name or is in wrong package), please create one"
        private val UI_CLASSES =  listOf(
            "android.support.v4.app.Fragment",
            "androidx.fragment.app.Fragment")
    }

    override fun applicableSuperClasses() = UI_CLASSES

    override fun visitClass(context: JavaContext, declaration: UClass) {

        // Ignore in case we're scanning one of the UI classes
        if (context.evaluator.getQualifiedName(declaration) in UI_CLASSES) return

        declaration.qualifiedName?.let { qualifiedName ->
            declaration.name?.let { name ->
                val packageName = qualifiedName.dropLast(name.length + 1)
                val robotName = name.dropLast("Fragment".length) + "Robot"
                if (context.evaluator.findClass("${packageName}.${robotName}") == null) {
                    context.report(
                        ISSUE_MISSING_ROBOT_DETECTOR, declaration,
                        context.getNameLocation(declaration),
                        MissingRobotDetector.MESSAGE
                    )
                }
            }
        }
    }
}
