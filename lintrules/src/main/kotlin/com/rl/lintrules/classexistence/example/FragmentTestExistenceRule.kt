package com.rl.lintrules.classexistence.example

import com.rl.lintrules.classexistence.ClassExistenceRule

class FragmentTestExistenceRule : ClassExistenceRule {
    override fun classShouldExist(packageName: String, className: String): List<String> {
        if (className.endsWith("Fragment")) {
            return listOf("${packageName}.${className}Test")
        }
        return emptyList()
    }

    override fun getMessage() = "This fragment doesn't have a test or it is not having the correct (package) name!"

}