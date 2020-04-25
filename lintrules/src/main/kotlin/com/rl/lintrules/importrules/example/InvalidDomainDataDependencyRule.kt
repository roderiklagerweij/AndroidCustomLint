package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidDomainDataDependencyRule : InvalidImportRule {
    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importedClass: String
    ) : Boolean =
        !(isDomainPackage(visitingPackage) && isDataPackage(importedClass))

    override fun getMessage() = "Domain classes should not import from UI package"

    private fun isDomainPackage(packageName : String) =
        packageName.contains(".domain.") || packageName.endsWith("domain")

    private fun isDataPackage(packageName : String) =
        packageName.contains(".data.") || packageName.endsWith("data")
}