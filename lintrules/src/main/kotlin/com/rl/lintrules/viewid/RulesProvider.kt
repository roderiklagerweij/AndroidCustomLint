package com.rl.lintrules.viewid

import com.rl.lintrules.viewid.example.AllCapsViewIdRule

object RulesProvider {
    var rulesList : List<ViewIdRule> = listOf(
        AllCapsViewIdRule()
    )
}