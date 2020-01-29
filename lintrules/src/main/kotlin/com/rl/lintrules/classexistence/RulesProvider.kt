package com.rl.lintrules.classexistence

import com.rl.lintrules.classexistence.example.FragmentTestExistenceRule
import com.rl.lintrules.classexistence.example.RobotExistenceRule
import com.rl.lintrules.classexistence.example.ViewModelExistenceRule

object RulesProvider {
    var rulesList : List<ClassExistenceRule> = listOf(
        ViewModelExistenceRule(),
        FragmentTestExistenceRule(),
        RobotExistenceRule()
    )
}