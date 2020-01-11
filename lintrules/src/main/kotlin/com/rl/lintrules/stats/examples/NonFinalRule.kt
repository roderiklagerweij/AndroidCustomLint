package com.rl.lintrules.stats.examples

import com.rl.lintrules.stats.ClassStats
import com.rl.lintrules.stats.ClassStatsRule

class NonFinalRule : ClassStatsRule {
    override fun isValid(classStats: ClassStats) =
        classStats.numberOfVars < 4


    override fun getMessage() = "Please don't use double bang operator"

}