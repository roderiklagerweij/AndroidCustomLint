package com.rl.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.rl.lintrules.classexistence.ISSUE_CLASS_EXISTENCE
import com.rl.lintrules.importrules.ISSUE_IMPORT_DETECTOR
import com.rl.lintrules.viewid.ISSUE_INCORRECT_VIEW_ID_DETECTOR

class MyIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            ISSUE_USER_INTERFACE_INHERITANCE,
            ISSUE_VIEW_STATE,
            ISSUE_MYINCORRECT_VIEW_ID_DETECTOR,
            ISSUE_INCORRECT_VIEW_ID_DETECTOR,
            ISSUE_IMPORT_DETECTOR,
            ISSUE_CLASS_EXISTENCE
        )

    override val api: Int = CURRENT_API
}
