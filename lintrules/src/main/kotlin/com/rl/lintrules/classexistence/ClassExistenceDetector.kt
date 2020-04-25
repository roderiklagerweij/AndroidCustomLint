package com.rl.lintrules.classexistence

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.rl.lintrules.classexistence.example.FragmentTestExistenceRule
import com.rl.lintrules.classexistence.example.RobotExistenceRule
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

    val rules = RulesProvider.rulesList

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitClass(node: UClass) {
                node.qualifiedName?.let { qualifiedName ->
                    node.name?.let { name ->

                        rules.forEach { classExistenceRule ->
                            val packageName = qualifiedName.dropLast(name.length + 1)
                            val classesShouldExist = classExistenceRule.classShouldExist(packageName, name)
                            classesShouldExist.forEach { classThatShouldExist ->
                                if (context.evaluator.findClass(classThatShouldExist) == null) {
                                    ISSUE_CLASS_EXISTENCE.defaultSeverity
                                    context.report(
                                        ISSUE_CLASS_EXISTENCE, node,
                                        context.getNameLocation(node),
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
