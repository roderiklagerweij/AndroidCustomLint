package com.rl.lintrules

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import java.util.*

val ISSUE_USER_INTERFACE_INHERITANCE = Issue.create(
    id = "UserInterfaceInheritance",
    briefDescription = "Bad inheritance",
    explanation = UserInterfaceInheritanceDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        UserInterfaceInheritanceDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE)))

class UserInterfaceInheritanceDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "Let activities and fragments inherit directly from Android's Activity or Fragment"
        private val UI_CLASSES =  listOf(
            "android.support.v7.app.AppCompatActivity",
            "androidx.appcompat.app.AppCompatActivity",
            "android.support.v4.app.Fragment",
            "androidx.fragment.app.Fragment")
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? = listOf(UClass::class.java)

    override fun applicableSuperClasses() = UI_CLASSES

    override fun visitClass(context: JavaContext, declaration: UClass) {
        super.visitClass(context, declaration)

        if (context.evaluator.getQualifiedName(declaration) in UI_CLASSES) return

        declaration.superClass?.let { superClass ->
            if (context.evaluator.getQualifiedName(superClass) !in UI_CLASSES) {
                context.report(
                    ISSUE_USER_INTERFACE_INHERITANCE, declaration,
                    context.getNameLocation(declaration),
                    MESSAGE
                )
            }
        }
    }
}