package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class IncorrectViewIdDetectorTest {

    @Test
    fun `if view id starts with the name of the layout file then expect clean`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/fragment_file_someid"
                />
              """
            ).indented())
            .issues(ISSUE_INCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `if view id doesn't start with the name of the layout file then expect error`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/test"
                />
              """
            ).indented())
            .issues(ISSUE_INCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun `if element has no id then expect clean`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView
                />
              """
            ).indented())
            .issues(ISSUE_INCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

}