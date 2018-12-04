package vars

import com.gotomeeting.jirasupport.*


@NonCPS
def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        deploymentKey1 = CreateIssue.createDeployment(jiraSupport, "WDO", "Deploy $jira_component_name to stage", description, new Date(), new Date(),
                IssueInfo.getIssueLink(jiraSupport, project_key), "Live")
        echo "Deployment <Live> created. Issue:"
        echo IssueInfo.getIssueLink(jiraSupport, deploymentKey1)
        deploymentKey2 = CreateIssue.createDeployment(jiraSupport, "WDO", "Deploy $jira_component_name to stage", description, new Date(), new Date(),
                IssueInfo.getIssueLink(jiraSupport, project_key), "Stage")
        echo "Deployment <Stage> created. Issue:"
        echo IssueInfo.getIssueLink(jiraSupport, deploymentKey2)
    } catch (Exception e) {
        echo e.message
    }

}

