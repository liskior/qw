package vars

import com.gotomeeting.jirasupport.*

@NonCPS
def call(key) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    RefactorItem.assignIssue(jiraSupport, key, assign_name)
}
