package com.rl.lintrules.importrules

import com.rl.lintrules.importrules.example.InvalidEspressoImportRule

object RulesProvider {
    var rulesList : List<InvalidImportRule> = listOf(
        InvalidEspressoImportRule()
    )
}