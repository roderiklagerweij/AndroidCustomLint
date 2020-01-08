package com.rl.lintrules

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class MyViewIdDetectorTest {

    @Test
    fun `if view id only has one part then an error is expected`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
            <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android">
                <TextView android:id="@+id/firstPart" />
            </LinearLayout>
              """
            ).indented())
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun `if view id only has two parts then an error is expected`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/firstPart_secondPart"
                />
              """
            ).indented())
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun `if view id has three parts then clean is expected`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/firstPart_secondPart_thirdPart"
                />
              """
            ).indented())
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `if view id contains non-camel case first part then error is expected`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/Firstpart_secondPart_thirdPart"
                />
              """
            ).indented())
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun `if view id contains non-camel case second part then error is expected`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/firstPart_Secondpart_thirdPart"
                />
              """
            ).indented())
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun `if view id contains non-camel case third part then error is expected`() {
        lint().files(
            TestFiles.xml(
                "res/layout/fragment_file.xml", """
          <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                android:id="@+id/firstPart_secondPart_Thirdpart"
                />
              """
            ).indented())
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
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
            .issues(ISSUE_MYINCORRECT_VIEW_ID_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}