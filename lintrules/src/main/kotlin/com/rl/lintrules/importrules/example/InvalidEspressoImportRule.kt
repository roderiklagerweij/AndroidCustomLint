package com.rl.lintrules.importrules.example

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidEspressoImportRule :
    InvalidImportRule {

    override fun isAllowedImport(visitingPackage : String, visitingClassName : String, importedClass: String): Boolean {
        if (visitingClassName.endsWith("Test") || visitingClassName.endsWith("Journey")) {
            if (importedClass.startsWith("androidx.test.espresso")) {
                return false
            }
        }
        return true
    }

    override fun getMessage() = "Please don't use any Espresso code in your test classes"
}