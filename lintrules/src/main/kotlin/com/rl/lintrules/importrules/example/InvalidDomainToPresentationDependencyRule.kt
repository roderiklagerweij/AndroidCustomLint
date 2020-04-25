package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidDomainToPresentationDependencyRule : InvalidImportRule {

    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ) : Boolean =
        !(isDomainPackage(visitingPackage)
                && isPresentationPackageImport(importStatement))

    override fun getMessage() =
        "Domain classes should not import from presentation package"

    private fun isDomainPackage(packageName : String) =
        packageName.contains(".domain.")
                || packageName.endsWith("domain")

    private fun isPresentationPackageImport(importStatement : String) =
        importStatement.contains(".presentation.")
                || importStatement.endsWith("presentation")
}