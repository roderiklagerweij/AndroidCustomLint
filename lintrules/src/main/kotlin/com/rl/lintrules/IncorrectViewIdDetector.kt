package com.rl.lintrules

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

val ISSUE_INCORRECT_VIEW_ID_DETECTOR = Issue.create(
    id = "IncorrectViewId",
    briefDescription = MissingRobotDetector.MESSAGE,
    explanation = MissingRobotDetector.MESSAGE,
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(
        IncorrectViewIdDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE))


class IncorrectViewIdDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType) = true

    override fun getApplicableElements() = ALL

    override fun visitElement(context: XmlContext, element: Element) {
        val fileName = context.file.name.split(".")[0]
        element.attributes.getNamedItem("android:id")?.let { viewId ->
            if (!viewId.nodeValue.split("/")[1].startsWith(fileName)) {
                context.report(ISSUE_INCORRECT_VIEW_ID_DETECTOR, element.attributes.getNamedItem("android:id"), context.getElementLocation(element), "View id should start with same name as filename for clarity")
            }
        }

//        element.children()
//            .forEach { child ->
//                val isStringResource = child.isTextNode() && SdkConstants.TAG_STRING == element.localName
//                val isStringArrayOrPlurals = child.isElementNode() && (SdkConstants.TAG_STRING_ARRAY == element.localName || SdkConstants.TAG_PLURALS == element.localName)
//
//                if (isStringResource) {
//                    checkText(context, element, child.nodeValue)
//                } else if (isStringArrayOrPlurals) {
//                    child.children()
//                        .filter { it.isTextNode() }
//                        .forEach { checkText(context, child, it.nodeValue) }
//                }
//            }
    }



}
