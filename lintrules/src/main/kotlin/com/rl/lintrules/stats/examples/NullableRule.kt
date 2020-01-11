package com.rl.lintrules.stats.examples

import com.rl.lintrules.stats.ClassStats
import com.rl.lintrules.stats.ClassStatsRule

class NullableRule : ClassStatsRule {
    override fun isValid(classStats: ClassStats) =
        classStats.numberOfNullableDeclarations < 4


    override fun getMessage() = "Please don't use nullables!"

}