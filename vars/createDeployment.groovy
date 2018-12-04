package vars

import com.gotomeeting.jirasupport.*


@NonCPS
def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    CreateIssue.createDeployment(jiraSupport, "WDO", "Deploy $jira_component_name to stage", description, new Date(), new Date(),
            IssueInfo.getIssueLink(jiraSupport, project_key), "Live")
    CreateIssue.createDeployment(jiraSupport, "WDO", "Deploy $jira_component_name to stage", description, new Date(), new Date(),
            IssueInfo.getIssueLink(jiraSupport, project_key), "Stage")
}

