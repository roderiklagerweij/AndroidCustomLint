package com.rl.lintrules.classexistence

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.rl.lintrules.classexistence.example.FragmentTestExistenceRule
import com.rl.lintrules.classexistence.example.ViewModelExistenceRule
import org.jetbrains.uast.UClass
import java.util.*

val ISSUE_CLASS_EXISTENCE = Issue.create(
    id = "MissingRobot",
    briefDescription = ClassExistenceDetector.MESSAGE,
    explanation = ClassExistenceDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        ClassExistenceDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE)))


class ClassExistenceDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "Lint detector for the existence of classes"
    }

    val rules = listOf(
        ViewModelExistenceRule(),
        FragmentTestExistenceRule()
    )

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitClass(declaration: UClass) {
                declaration.qualifiedName?.let { qualifiedName ->
                    declaration.name?.let { name ->

                        rules.forEach { classExistenceRule ->
                            val packageName = qualifiedName.dropLast(name.length + 1)
                            val classesShouldExist = classExistenceRule.classShouldExist(packageName, name)
                            classesShouldExist.forEach { classThatShouldExist ->
                                if (context.evaluator.findClass(classThatShouldExist) == null) {
                                    context.report(
                                        ISSUE_CLASS_EXISTENCE, declaration,
                                        context.getNameLocation(declaration),
                                        classExistenceRule.getMessage()
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
