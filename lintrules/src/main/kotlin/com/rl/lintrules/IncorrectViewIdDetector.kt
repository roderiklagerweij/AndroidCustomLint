package com.rl.lintrules

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
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

    override fun getApplicableAttributes(): Collection<String>? =
        listOf(SdkConstants.ATTR_ID)

    override fun appliesTo(folderType: ResourceFolderType) =
        folderType == ResourceFolderType.LAYOUT

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (attribute.name != null && attribute.name.equals("android:id")) {
            if (!isValidId(attribute.value)) {
                context.report(
                    ISSUE_INCORRECT_VIEW_ID_DETECTOR,
                    attribute,
                    context.getLocation(attribute),
                    "This view does not have a valid view id. Valid view id is part1_part2_part3 where all parts are in camelcase"
                )
            }
        }
    }

    private fun isValidId( id: String) : Boolean {
        val trimmedId = id.split("/")[1] // remove @+id/
        val splits = trimmedId.split("_")
        if (splits.count() != 3) {
            return false
        }

        if (!splits[0].isDefinedCamelCase() ||
            !splits[1].isDefinedCamelCase() ||
            !splits[2].isDefinedCamelCase()) {
            return false
        }

        return true
    }

    private fun String.isDefinedCamelCase(): Boolean {
        if (length > 0 && getOrNull(0)!!.isUpperCase()) {
            return false
        }

        val charArray = toCharArray()
        return charArray
            .mapIndexed { index, current ->
                current to charArray.getOrNull(index + 1)
            }
            .none {
                it.first.isUpperCase() && it.second?.isUpperCase() ?: false
            }
    }
}
