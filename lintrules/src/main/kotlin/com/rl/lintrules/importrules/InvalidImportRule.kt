package com.rl.lintrules.importrules


interface InvalidImportRule {
    fun isAllowedImport(className : String, isTestSource : Boolean, importedClass: String) : Boolean
    fun getMessage(): String
}
