package com.rl.lintrules.viewid

interface ViewIdRule {
    fun isValidId(id: String, viewType : String, layoutName : String) : Boolean
    fun getMessage() : String
}