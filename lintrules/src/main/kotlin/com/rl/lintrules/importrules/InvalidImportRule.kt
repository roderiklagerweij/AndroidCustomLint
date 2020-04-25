package com.rl.lintrules.importrules


interface InvalidImportRule {

    // Return true if allowed import, false if violation
    fun isAllowedImport(
        visitingPackage : String,
        visitingClassName : String,
        importStatement: String) : Boolean

    // Message to show developer in case of not allowed import
    fun getMessage(): String
}
