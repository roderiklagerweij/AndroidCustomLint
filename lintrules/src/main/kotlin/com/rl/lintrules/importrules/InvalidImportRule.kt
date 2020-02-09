package com.rl.lintrules.importrules


interface InvalidImportRule {
    fun isAllowedImport(visitingPackage : String?, visitingClassName : String, importedClass: String) : Boolean
    fun getMessage(): String
}
