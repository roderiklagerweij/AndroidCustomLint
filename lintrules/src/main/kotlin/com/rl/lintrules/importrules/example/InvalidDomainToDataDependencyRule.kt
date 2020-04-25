package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidDomainToDataDependencyRule : InvalidImportRule {

    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ) : Boolean =
        !(isDomainPackage(visitingPackage)
                && isDataPackage(importStatement))

    override fun getMessage() =
        "Domain classes should not import from data package"

    private fun isDomainPackage(packageName : String) =
        packageName.contains(".domain.")
                || packageName.endsWith("domain")

    private fun isDataPackage(importStatement : String) =
        importStatement.contains(".data.")
                || importStatement.endsWith("data")
}