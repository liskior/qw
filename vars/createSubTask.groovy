package vars

import com.gotomeeting.jirasupport.*
@NonCPS
def call(release_key) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    key = CreateIssue.createIssue(jiraSupport, project_key, env.STAGE_NAME, "bla", release_key)
    echo "Subtask link:"
    echo IssueInfo.getIssueLink(jiraSupport, key)
    return key

}
