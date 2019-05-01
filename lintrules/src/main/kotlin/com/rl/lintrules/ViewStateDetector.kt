package com.rl.lintrules

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import java.util.*


val ISSUE_VIEW_STATE = Issue.create(
    id = "ViewState",
    briefDescription = "bla",
    explanation = ViewStateDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        ViewStateDetector::class.java,
        EnumSet.of(Scope.JAVA_FILE)))


class ViewStateDetector : Detector(), Detector.UastScanner {

    companion object {
        const val MESSAGE = "asd"
        private val UI_CLASSES =  listOf(
            "android.support.v7.app.AppCompatActivity",
            "androidx.appcompat.app.AppCompatActivity",
            "android.support.v4.app.Fragment",
            "androidx.fragment.app")
    }

    override fun applicableSuperClasses(): List<String>? = UI_CLASSES

    override fun visitClass(context: JavaContext, declaration: UClass) {
        declaration.fields.forEach {
            if (!it.type.canonicalText.startsWith("android")) {
                context.report(
                    ISSUE_VIEW_STATE, it,
                    context.getLocation(it),
                    MESSAGE)
            }
        }
    }
}