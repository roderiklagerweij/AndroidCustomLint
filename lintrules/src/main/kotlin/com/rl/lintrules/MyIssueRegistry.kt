package com.rl.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class MyIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            ISSUE_USER_INTERFACE_INHERITANCE,
            ISSUE_VIEW_STATE
        )

    override val api: Int = CURRENT_API
}
