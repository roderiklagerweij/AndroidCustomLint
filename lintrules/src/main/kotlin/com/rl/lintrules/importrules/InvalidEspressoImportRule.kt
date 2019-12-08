package com.rl.lintrules.importrules

class InvalidEspressoImportRule : InvalidImportRule {

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
}