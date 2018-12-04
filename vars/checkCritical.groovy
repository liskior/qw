package vars

import com.gotomeeting.jirasupport.*

def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        for (String issue : CheckCriticalIssues.checkCriticalIssues(jiraSupport, jira_component_name)) {
            println(issue)
        }
    } catch (Exception e) {
        echo e.message
    }
}