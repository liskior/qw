import com.gotomeeting.jirasupport.*

def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        def str = "Critical Issues:\n"
        for (String issue : CheckCriticalIssues.checkCriticalIssues(jiraSupport, jira_component_name)) {
            str += issue
        }
        echo str
    } catch (Exception e) {
        echo e.message
    }
}