package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidDomainUIDependencyRule : InvalidImportRule {
    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importedClass: String
    ) : Boolean =
        !(isDomainPackage(visitingPackage) && isUiPackage(importedClass))

    override fun getMessage() = "Domain classes should not import from UI package"

    private fun isDomainPackage(packageName : String) =
        packageName.contains(".domain.") || packageName.endsWith("domain")

    private fun isUiPackage(packageName : String) =
        packageName.contains(".ui.") || packageName.endsWith("ui")
}