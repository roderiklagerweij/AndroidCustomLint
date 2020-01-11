package com.rl.lintrules.stats.examples

import com.rl.lintrules.stats.ClassStats
import com.rl.lintrules.stats.ClassStatsRule

class DoubleBangRule : ClassStatsRule {
    override fun isValid(classStats: ClassStats) =
        classStats.numberOfDoubleBangs == 0


    override fun getMessage() = "Please don't use double bang operator"

}