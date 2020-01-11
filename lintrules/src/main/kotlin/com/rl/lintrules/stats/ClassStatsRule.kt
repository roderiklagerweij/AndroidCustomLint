package com.rl.lintrules.stats

interface ClassStatsRule {
    fun isValid(classStats : ClassStats) : Boolean
    fun getMessage() : String
}