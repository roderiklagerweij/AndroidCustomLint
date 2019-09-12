package com.rl.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class MyIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            ISSUE_USER_INTERFACE_INHERITANCE,
            ISSUE_VIEW_STATE,
//            ISSUE_MISSING_ROBOT_DETECTOR,
//            ISSUE_MISSING_VIEWMODEL_DETECTOR,
            ISSUE_INCORRECT_VIEW_ID_DETECTOR
        )

    override val api: Int = CURRENT_API
}
