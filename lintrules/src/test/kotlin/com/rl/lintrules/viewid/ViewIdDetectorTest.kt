package com.rl.lintrules.viewid

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.rl.lintrules.viewid.ISSUE_INCORRECT_VIEW_ID_DETECTOR
import org.junit.Test

class ViewIdDetectorTest {

    @Test
    fun `if view id is not uppercase expect warning`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
            <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android">
                <TextView android:id="@+id/someidlowercase" />
            </LinearLayout>
              """
            ).indented())
            .issues(ISSUE_INCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun `if view id is all uppercase expect clean`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
            <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android">
                <TextView android:id="@+id/SOMEIDUPPERCASE" />
            </LinearLayout>
              """
            ).indented())
            .issues(ISSUE_INCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

}