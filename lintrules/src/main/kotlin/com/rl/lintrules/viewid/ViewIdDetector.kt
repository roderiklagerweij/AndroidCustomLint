package com.rl.lintrules.viewid

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import com.rl.lintrules.viewid.example.AllCapsViewIdRule
import org.w3c.dom.Attr

val ISSUE_INCORRECT_VIEW_ID_DETECTOR = Issue.create(
    id = "IncorrectViewId",
    briefDescription = IncorrectViewIdDetector.MESSAGE,
    explanation = IncorrectViewIdDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.WARNING,
    implementation = Implementation(
        IncorrectViewIdDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE))

class IncorrectViewIdDetector : LayoutDetector() {

    companion object {
        const val MESSAGE = "Lint detector for properly naming view id's"
    }

    val rules = listOf(AllCapsViewIdRule())

    override fun getApplicableAttributes(): Collection<String>? =
        listOf(SdkConstants.ATTR_ID)

    override fun appliesTo(folderType: ResourceFolderType) =
        folderType == ResourceFolderType.LAYOUT

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (attribute.name != null && attribute.name == "android:id") {
            rules.forEach {
                val id = attribute.value.split("/")[1] // remove @+id/
                val classType = "todo"
                val layoutName = "todo"

                if (!it.isValidId(id, classType, layoutName)) {
                    context.report(
                        ISSUE_INCORRECT_VIEW_ID_DETECTOR,
                        attribute,
                        context.getLocation(attribute),
                        it.getMessage()
                    )
                }
            }
        }
    }
}
