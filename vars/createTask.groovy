package vars

import com.gotomeeting.jirasupport.*


@NonCPS
def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    release_key = CreateIssue.createIssue(jiraSupport, project_key, project_key + version, description)
    echo IssueInfo.getIssueLink(jiraSupport, release_key)
    return release_key

}
