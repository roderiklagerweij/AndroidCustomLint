package com.rl.lintrules.importrules

import com.rl.lintrules.importrules.example.InvalidDomainDataDependencyRule
import com.rl.lintrules.importrules.example.InvalidDomainUIDependencyRule
import com.rl.lintrules.importrules.example.InvalidEspressoImportRule
import com.rl.lintrules.importrules.example.InvalidFeatureImportRule

object RulesProvider {
    var rulesList : List<InvalidImportRule> = listOf(
        InvalidDomainUIDependencyRule(),
        InvalidDomainDataDependencyRule(),
        InvalidFeatureImportRule(),
        InvalidEspressoImportRule()
    )
}