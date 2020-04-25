package com.rl.testlintapplication.presentation

import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private val testFlag = true

    companion object {
        const val SOME_CONSTANT = 1
    }
}