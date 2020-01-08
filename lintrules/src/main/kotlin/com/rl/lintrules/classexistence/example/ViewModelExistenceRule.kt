package com.rl.lintrules.classexistence.example

import com.rl.lintrules.classexistence.ClassExistenceRule

class ViewModelExistenceRule : ClassExistenceRule {
    override fun classShouldExist(packageName: String, className: String): List<String> {
        if (className.endsWith("Fragment")) {
            return listOf("${packageName}.${className.dropLast("Fragment".length)}ViewModel")
        }
        return emptyList()
    }

    override fun getMessage() = "ViewModel class is missing for this fragment!"

}