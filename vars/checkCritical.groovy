package vars

import com.gotomeeting.jirasupport.*

def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        echo "Critical Issues:"
        for (String issue : CheckCriticalIssues.checkCriticalIssues(jiraSupport, jira_component_name)) {
            echo issue
        }
    } catch (Exception e) {
        echo e.message
    }
}