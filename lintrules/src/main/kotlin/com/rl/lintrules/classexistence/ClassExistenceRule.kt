package com.rl.lintrules.classexistence

interface ClassExistenceRule {

    fun classShouldExist(packageName: String, className : String) : List<String>
    fun getMessage(): String
}