package com.rl.lintrules.classexistence.example

import com.rl.lintrules.classexistence.ClassExistenceRule

class RobotExistenceRule : ClassExistenceRule {
    override fun classShouldExist(packageName: String, className: String): List<String> {
        if (className.endsWith("Fragment")) {
            return listOf("${packageName}.${className.dropLast("Fragment".length)}Robot")
        }
        return emptyList()
    }

    override fun getMessage() = "Robot class is missing for this fragment!"

}