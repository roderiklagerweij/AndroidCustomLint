package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidFeatureImportRule : InvalidImportRule {
    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importedClass: String
    ): Boolean {
        return !(isFeaturePackage(visitingPackage) &&
                isFeaturePackage(importedClass) &&
                isDifferentFeaturePackage(visitingPackage, importedClass))
    }

    override fun getMessage() = "Please don't reference other features"

    private fun isFeaturePackage(packageName: String): Boolean =
        // please do something more sophisticated here than I did
        packageName.contains(".features.")

    private fun isDifferentFeaturePackage(packageName1: String, packageName2: String) =
        packageName1.split(".features.")[1].split(".")[0] !=
                packageName2.split(".features.")[1].split(".")[0]

}