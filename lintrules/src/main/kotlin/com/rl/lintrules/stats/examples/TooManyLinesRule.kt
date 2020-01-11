package com.rl.lintrules.stats.examples

import com.rl.lintrules.stats.ClassStats
import com.rl.lintrules.stats.ClassStatsRule

class TooManyLinesRule : ClassStatsRule {
    override fun isValid(classStats: ClassStats) =
        classStats.numberOfLines <= 5


    override fun getMessage() = "Too many lines for class!"

}