package com.rl.lintrules.importrules

import com.rl.lintrules.importrules.example.InvalidDomainToDataDependencyRule
import com.rl.lintrules.importrules.example.InvalidDomainToPresentationDependencyRule
import com.rl.lintrules.importrules.example.InvalidEspressoImportRule
import com.rl.lintrules.importrules.example.InvalidFeatureImportRule

object RulesProvider {
    var rulesList : List<InvalidImportRule> = listOf(
        InvalidDomainToPresentationDependencyRule(),
        InvalidDomainToDataDependencyRule(),
        InvalidFeatureImportRule(),
        InvalidEspressoImportRule()
    )
}