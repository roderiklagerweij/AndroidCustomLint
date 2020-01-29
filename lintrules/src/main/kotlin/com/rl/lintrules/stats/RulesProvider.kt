package com.rl.lintrules.stats

import com.rl.lintrules.stats.examples.DoubleBangRule
import com.rl.lintrules.stats.examples.NonFinalRule
import com.rl.lintrules.stats.examples.NullableRule
import com.rl.lintrules.stats.examples.TooManyLinesRule

object RulesProvider {
    var rulesList : List<ClassStatsRule> = listOf(
        TooManyLinesRule(),
        DoubleBangRule(),
        NonFinalRule(),
        NullableRule()
    )
}