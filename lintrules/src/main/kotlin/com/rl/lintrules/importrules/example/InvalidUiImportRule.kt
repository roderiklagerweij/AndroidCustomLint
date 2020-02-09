package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidUiImportRule : InvalidImportRule {
    override fun isAllowedImport(
        visitingPackage: String?,
        visitingClassName: String,
        importedClass: String
    ) : Boolean =
        !(isDomainPackage(visitingPackage) && isUiPackage(importedClass))

    override fun getMessage() = "Classes in domain layer should not reference UI classes"

    private fun isDomainPackage(packageName : String?) =
        packageName.contains(".domain.") || packageName.endsWith("domain")

    private fun isUiPackage(packageName : String) =
        packageName.contains(".ui.") || packageName.endsWith("ui")
}