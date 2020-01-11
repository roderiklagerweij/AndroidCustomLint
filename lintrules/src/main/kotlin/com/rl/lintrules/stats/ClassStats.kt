package com.rl.lintrules.stats

data class ClassStats(
    var numberOfLines : Int,
    var numberOfDoubleBangs : Int,
    var numberOfVars : Int,
    var numberOfNullableDeclarations : Int
)