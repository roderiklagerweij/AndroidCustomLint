package com.rl.lintrules.importrules.examples

import com.rl.lintrules.importrules.InvalidImportRule

class InvalidEspressoImportRule :
    InvalidImportRule {

    override fun isAllowedImport(className: String, isTestSource : Boolean, importedClass: String): Boolean {
        if (isTestSource) {
            if (className.endsWith("Test") || className.endsWith("Journey")) {
                if (importedClass.startsWith("androidx.test.espresso")) {
                    return false
                }
            }
        }
        return true
    }

    override fun getMessage() = "Please don't use any Espresso code in your test classes"
}