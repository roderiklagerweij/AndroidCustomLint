package com.rl.lintrules.classexistence

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.rl.lintrules.classexistence.ISSUE_CLASS_EXISTENCE
import com.rl.lintrules.classexistence.RulesProvider
import com.rl.lintrules.classexistence.example.FragmentTestExistenceRule
import com.rl.lintrules.classexistence.example.ViewModelExistenceRule
import org.junit.Test

class ClassExistenceDetectorTest {

    @Test
    fun `when for a fragment there is no viewmodel then expect error`() {
        RulesProvider.rulesList = listOf(ViewModelExistenceRule())

        lint().files(kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() 
            
        """).indented())
            .issues(ISSUE_CLASS_EXISTENCE)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when for a fragment there is a viewmodel then expect clean`() {
        RulesProvider.rulesList = listOf(ViewModelExistenceRule())

        lint().files(kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() 
            
            class SomeViewModel

        """).indented())
            .issues(ISSUE_CLASS_EXISTENCE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `when for a fragment there is no fragment test then expect error`() {
        RulesProvider.rulesList = listOf(FragmentTestExistenceRule())

        lint().files(kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() 
            
        """).indented())
            .issues(ISSUE_CLASS_EXISTENCE)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `when for a fragment there is a fragment test then expect clean`() {
        RulesProvider.rulesList = listOf(FragmentTestExistenceRule())

        lint().files(kotlin("""
            package test

            import android.support.v4.app.Fragment

            class SomeFragment : Fragment() 
            
            class SomeFragmentTest
        """).indented())
            .issues(ISSUE_CLASS_EXISTENCE)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}