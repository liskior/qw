import com.gotomeeting.jirasupport.*

@NonCPS
def call(key) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        RefactorItem.assignIssue(jiraSupport, key, assign_name)
        echo "Assigned successfull"
    } catch (Exception e) {
        echo e.message
    }
}
