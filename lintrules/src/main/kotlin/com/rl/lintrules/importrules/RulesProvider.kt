package com.rl.lintrules.importrules

import com.rl.lintrules.importrules.example.InvalidEspressoImportRule
import com.rl.lintrules.importrules.example.InvalidUiImportRule

object RulesProvider {
    var rulesList : List<InvalidImportRule> = listOf(
        InvalidEspressoImportRule(),
        InvalidUiImportRule()
    )
}